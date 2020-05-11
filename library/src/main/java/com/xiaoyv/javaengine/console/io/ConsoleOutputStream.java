package com.xiaoyv.javaengine.console.io;

import androidx.annotation.NonNull;

import com.xiaoyv.javaengine.console.JavaConsole;
import com.xiaoyv.javaengine.console.io.queue.ByteQueue;

import java.io.OutputStream;


/**
 * 代理系统 System.out
 */
public class ConsoleOutputStream extends OutputStream {
    private ByteQueue mStdoutBuffer;
    private JavaConsole.StdListener listener;


    public ConsoleOutputStream(ByteQueue mStdoutBuffer, JavaConsole.StdListener listener) {
        this.mStdoutBuffer = mStdoutBuffer;
        this.listener = listener;
    }

    @Override
    public void write(int b) {
        write(new byte[]{(byte) b}, 0, 1);
    }

    @Override
    public void write(@NonNull byte[] b, int off, int len) {
        try {
            mStdoutBuffer.write(b, off, len);
            listener.onUpdate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}