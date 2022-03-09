package com.xiaoyv.java.compiler.tools.exec.io

import com.xiaoyv.java.compiler.tools.exec.io.queue.ByteQueue
import com.xiaoyv.java.compiler.tools.exec.io.queue.ByteQueueListener
import java.io.OutputStream

/**
 * 代理系统 System.out
 *
 * @author Admin
 */
class JavaOutputStream(
    private val byteQueue: ByteQueue,
    private val listener: ByteQueueListener
) : OutputStream() {

    override fun write(b: Int) {
        write(byteArrayOf(b.toByte()), 0, 1)
    }

    override fun write(b: ByteArray, off: Int, len: Int) {
        try {
            byteQueue.write(b, off, len)
            listener.onUpdate()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}