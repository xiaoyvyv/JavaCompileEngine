package com.xiaoyv.javaengine;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;


@SuppressLint("Registered")
public class JavaEngineApplication extends Application {
    private ArrayList<PrintStream> out = new ArrayList<>();
    private ArrayList<PrintStream> err = new ArrayList<>();

    private InterceptorOutputStream systemOut;
    private InterceptorOutputStream systemErr;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化
        JavaEngine.init(this);

        // 系统输出拦截器
        systemOut = new InterceptorOutputStream(System.out, out);
        systemErr = new InterceptorOutputStream(System.err, err);

        // 设置拦截器
        System.setOut(systemOut);
        System.setErr(systemErr);

        //for log cat
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public void addStdOut(PrintStream out) {
        systemOut.add(out);
    }

    public void addStdErr(PrintStream err) {
        systemErr.add(err);
    }

    public void removeStdOut(PrintStream out) {
        systemOut.remove(out);
    }

    public void removeStdErr(PrintStream err) {
        systemErr.remove(err);
    }

    private static class InterceptorOutputStream extends PrintStream {
        private ArrayList<PrintStream> streams;

        InterceptorOutputStream(@NonNull OutputStream file, ArrayList<PrintStream> streams) {
            super(file, true);
            this.streams = streams;
        }

        public void add(PrintStream out) {
            this.streams.add(out);
        }

        public void remove(PrintStream out) {
            this.streams.remove(out);
        }

        @Override
        public void write(@NonNull byte[] buf, int off, int len) {
            super.write(buf, off, len);
            if (streams != null) {
                for (PrintStream printStream : streams) {
                    printStream.write(buf, off, len);
                }
            }
        }
    }

}
