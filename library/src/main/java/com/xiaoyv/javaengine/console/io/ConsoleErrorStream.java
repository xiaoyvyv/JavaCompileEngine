package com.xiaoyv.javaengine.console.io;

import androidx.annotation.NonNull;

import com.xiaoyv.javaengine.console.JavaConsole;
import com.xiaoyv.javaengine.console.io.queue.ByteQueue;

import java.io.OutputStream;

/**
 * 代理系统 System.err
 */
public class ConsoleErrorStream extends OutputStream {
    private ByteQueue mStderrBuffer;
    private JavaConsole.StdListener stdListener;


    public ConsoleErrorStream(ByteQueue mStderrBuffer, JavaConsole.StdListener stdListener) {
        this.mStderrBuffer = mStderrBuffer;
        this.stdListener = stdListener;

    }

    @Override
    public void write(@NonNull byte[] b, int off, int len) {
        try {
            mStderrBuffer.write(b, off, len);
            stdListener.onUpdate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(int b) {
        write(new byte[]{(byte) b}, 0, 1);
    }
}