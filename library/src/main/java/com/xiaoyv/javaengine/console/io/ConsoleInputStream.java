package com.xiaoyv.javaengine.console.io;

import android.util.Log;

import androidx.annotation.NonNull;

import com.xiaoyv.javaengine.console.io.queue.ByteQueue;

import java.io.InputStream;

/**
 * 代理系统 System.in
 */
public class ConsoleInputStream extends InputStream {
    private final Object mLock = new Object();

    @NonNull
    private ByteQueue mInputBuffer;

    public ConsoleInputStream(@NonNull ByteQueue mInputBuffer) {
        this.mInputBuffer = mInputBuffer;
    }


    @Override
    public int read() {
        synchronized (mLock) {
            try {
                return mInputBuffer.read(new byte[]{}, 0, 0);
            } catch (InterruptedException e) {
                return -1;
            }
        }
    }

    @Override
    public int read(@NonNull byte[] b, int off, int len) {
        synchronized (mLock) {
            try {
                return mInputBuffer.read(b, off, len);
            } catch (InterruptedException e) {
                return -1;
            }
        }
    }
}