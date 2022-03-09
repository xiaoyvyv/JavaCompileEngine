package com.xiaoyv.java.compiler.tools.exec.io

import com.xiaoyv.java.compiler.tools.exec.io.queue.ByteQueue
import java.io.InputStream

/**
 * 代理系统 System.in
 *
 * @author Admin
 */
class JavaInputStream(private val byteQueue: ByteQueue) : InputStream() {
    private val mLock = Any()

    override fun read(): Int {
        synchronized(mLock) {
            return try {
                byteQueue.read(byteArrayOf(), 0, 0)
            } catch (e: InterruptedException) {
                -1
            }
        }
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        synchronized(mLock) {
            return try {
                byteQueue.read(b, off, len)
            } catch (e: InterruptedException) {
                -1
            }
        }
    }
}