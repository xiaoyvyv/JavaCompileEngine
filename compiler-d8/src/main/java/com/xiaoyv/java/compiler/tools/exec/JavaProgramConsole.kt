package com.xiaoyv.java.compiler.tools.exec

import com.xiaoyv.java.compiler.JavaEngine
import com.xiaoyv.java.compiler.tools.exec.io.JavaInputStream
import com.xiaoyv.java.compiler.tools.exec.io.JavaOutputStream
import com.xiaoyv.java.compiler.tools.exec.io.queue.ByteQueue
import com.xiaoyv.java.compiler.tools.exec.io.queue.ByteQueueListener
import kotlinx.coroutines.*
import java.io.InputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets
import java.util.concurrent.atomic.AtomicBoolean

class JavaProgramConsole : CoroutineScope by MainScope() {
    /**
     * System.in
     */
    private lateinit var inputStream: InputStream

    /**
     * System.out
     */
    private lateinit var outputStream: PrintStream

    /**
     * System.err
     */
    private lateinit var errorStream: PrintStream

    /**
     * uses for 输入
     */
    private val inputBuffer = ByteQueue(ByteQueue.QUEUE_SIZE)

    /**
     * buffer for 正常输出
     */
    private val stdoutBuffer = ByteQueue(ByteQueue.QUEUE_SIZE)

    /**
     * buffer for 错误删除
     */
    private val stderrBuffer = ByteQueue(ByteQueue.QUEUE_SIZE)

    private val receiveBuffer = ByteArray(ByteQueue.QUEUE_SIZE)

    /**
     * 程序是否在运行
     */
    private val isRunning = AtomicBoolean(false)

    /**
     * 日志回调
     */
    var logNormalListener: CoroutineScope.(String) -> Unit = {}
    var logErrorListener: CoroutineScope.(String) -> Unit = {}

    internal fun interceptSystemPrint() {
        var length = 0

        inputStream = JavaInputStream(inputBuffer)
        outputStream = PrintStream(JavaOutputStream(stdoutBuffer, object : ByteQueueListener {
            override fun onUpdate() {
                if (!isRunning.get()) {
                    return
                }
                val bytesAvailable = stdoutBuffer.bytesAvailable
                val bytesToRead = bytesAvailable.coerceAtMost(receiveBuffer.size)

                runCatching {
                    val bytesRead = stdoutBuffer.read(receiveBuffer, 0, bytesToRead)
                    val out = String(receiveBuffer, 0, bytesRead, StandardCharsets.UTF_8)
                    length += out.length

                    launch(Dispatchers.Main) {
                        logNormalListener.invoke(this, out)
                    }
                }
            }
        }))
        errorStream = PrintStream(JavaOutputStream(stderrBuffer, object : ByteQueueListener {
            override fun onUpdate() {
                if (!isRunning.get()) {
                    return
                }
                val bytesAvailable = stderrBuffer.bytesAvailable
                val bytesToRead = bytesAvailable.coerceAtMost(receiveBuffer.size)

                runCatching {
                    val bytesRead = stderrBuffer.read(receiveBuffer, 0, bytesToRead)
                    val err = String(receiveBuffer, 0, bytesRead, StandardCharsets.UTF_8)
                    length += err.length

                    launch(Dispatchers.Main) {
                        logErrorListener.invoke(this, err)
                    }
                }
            }
        }))

        isRunning.set(true)

        // 置空
        inputBuffer.reset()
        stdoutBuffer.reset()
        stderrBuffer.reset()

        // 设置代理流
        System.setIn(inputStream)
        System.setOut(outputStream)
        System.setErr(errorStream)
    }

    /**
     * 写入用户输入信息
     *
     * @param stdin 输入信息
     */
    fun inputStdin(stdin: String): Boolean {
        if (!isRunning.get()) {
            return false
        }
        runCatching {
            // 获取输入信息的字节
            val bytes = stdin.toByteArray()
            // 向输入流写入
            inputBuffer.write(bytes, 0, bytes.size)
            // 提交
            val submitBytes = byteArrayOf('\n'.code.toByte(), (-1).toByte())
            inputBuffer.write(submitBytes, 0, submitBytes.size)
            return true
        }.onFailure {
            it.printStackTrace()
        }
        return false
    }

    /**
     * 关闭程序运行
     */
    fun close() {
        isRunning.set(false)
        JavaEngine.resetStdStream()

        // 置空
        inputBuffer.reset()
        stdoutBuffer.reset()
        stderrBuffer.reset()

        // 取消作用域
        cancel()

        if (JavaEngine.lastProgram == this) {
            JavaEngine.lastProgram = null
        }

        // 关闭流
        runCatching {
            inputStream.close()
        }
        runCatching {
            outputStream.close()
        }
        runCatching {
            errorStream.close()
        }
    }
}

