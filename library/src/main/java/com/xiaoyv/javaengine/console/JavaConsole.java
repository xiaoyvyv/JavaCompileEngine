package com.xiaoyv.javaengine.console;

import android.app.Application;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;

import com.xiaoyv.javaengine.JavaEngineApplication;
import com.xiaoyv.javaengine.console.io.ConsoleErrorStream;
import com.xiaoyv.javaengine.console.io.ConsoleInputStream;
import com.xiaoyv.javaengine.console.io.ConsoleOutputStream;
import com.xiaoyv.javaengine.console.io.queue.ByteQueue;
import com.xiaoyv.javaengine.utils.Utils;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Java控制台，代理系统输出和输入
 *
 * @author 王怀玉
 * @since 2020/5/11
 */
public class JavaConsole {
    private static final int NEW_OUTPUT = 1;
    private static final int NEW_ERR = 2;

    /**
     * text 长度
     */
    private int mLength = 0;

    /**
     * System.in
     */
    private InputStream mInputStream;

    /**
     * System.out
     */
    private PrintStream mOutputStream;

    /**
     * System.err
     */
    private PrintStream mErrorStream;

    /**
     * uses for 输入
     */
    private ByteQueue mInputBuffer = new ByteQueue(ByteQueue.QUEUE_SIZE);

    /**
     * buffer for 正常输出
     */
    private ByteQueue mStdoutBuffer = new ByteQueue(ByteQueue.QUEUE_SIZE);

    /**
     * buffer for 错误删除
     */
    private ByteQueue mStderrBuffer = new ByteQueue(ByteQueue.QUEUE_SIZE);

    private AppendStdListener appendStdListener;


    /**
     * 程序是否在运行
     */
    private AtomicBoolean mIsRunning = new AtomicBoolean(false);


    private byte[] mReceiveBuffer;

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (!mIsRunning.get()) {
                return;
            }
            if (msg.what == NEW_OUTPUT) {
                printStdOut();
            } else if (msg.what == NEW_ERR) {
                printStderr();
            }
        }
    };

    public JavaConsole(@NonNull AppendStdListener appendStdListener) {
        this.appendStdListener = appendStdListener;
        createIOStream();
    }

    /**
     * @return 当前控制台是否正在运行（挂起状态，如等待输入）
     */
    public boolean isRunning() {
        return mIsRunning.get();
    }

    /**
     * 初始化IOStream
     */
    private void createIOStream() {
        mReceiveBuffer = new byte[ByteQueue.QUEUE_SIZE];
        mStdoutBuffer = new ByteQueue(ByteQueue.QUEUE_SIZE);
        mStderrBuffer = new ByteQueue(ByteQueue.QUEUE_SIZE);

        mInputStream = new ConsoleInputStream(mInputBuffer);
        mOutputStream = new PrintStream(new ConsoleOutputStream(mStdoutBuffer, new StdListener() {
            @Override
            public void onUpdate() {
                mHandler.sendMessage(mHandler.obtainMessage(NEW_OUTPUT));
            }
        }));
        mErrorStream = new PrintStream(new ConsoleErrorStream(mStderrBuffer, new StdListener() {
            @Override
            public void onUpdate() {
                mHandler.sendMessage(mHandler.obtainMessage(NEW_ERR));
            }
        }));
    }

    /**
     * 输出正常信息
     */
    private void printStdOut() {
        int bytesAvailable = mStdoutBuffer.getBytesAvailable();
        int bytesToRead = Math.min(bytesAvailable, mReceiveBuffer.length);
        try {
            int bytesRead = mStdoutBuffer.read(mReceiveBuffer, 0, bytesToRead);
            final String out = new String(mReceiveBuffer, 0, bytesRead, StandardCharsets.UTF_8);
            mLength = mLength + out.length();

            // 主线程回调
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    appendStdListener.printStdout(out);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出错误信息
     */
    private void printStderr() {
        int bytesAvailable = mStderrBuffer.getBytesAvailable();
        int bytesToRead = Math.min(bytesAvailable, mReceiveBuffer.length);
        try {
            int bytesRead = mStderrBuffer.read(mReceiveBuffer, 0, bytesToRead);
            final String err = new String(mReceiveBuffer, 0, bytesRead, StandardCharsets.UTF_8);
            mLength = mLength + err.length();

            // 主线程回调
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // 错误信息添加红色
                    SpannableString spannableString = new SpannableString(err);
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, err.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    appendStdListener.printStderr(spannableString);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入用户输入信息
     *
     * @param stdin 输入信息
     */
    public boolean inputStdin(String stdin) {
        if (!mIsRunning.get()) {
            return false;
        }
        try {
            // 获取输入信息的字节
            byte[] bytes = stdin.getBytes();
            // 向输入流写入
            mInputBuffer.write(bytes, 0, bytes.length);
            // 提交
            return submitInput();
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 提交输入信息
     *
     * @return 是否提交成功
     */
    private boolean submitInput() {
        if (!mIsRunning.get()) {
            return false;
        }
        // 输入完成后追加一个回车符和数值-1，代表输入完成，后面读取不到输入数据了
        try {
            mInputBuffer.write(new byte[]{(byte) '\n', (byte) -1}, 0, 2);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    /**
     * 启动控制台
     */
    public void start() {
        // 启动系统输出输入代理
        turnOnSystemInputProxy();
        turnOnSystemOutputProxy();
        // 启动输出信息传递 Handler
        mIsRunning.set(true);
    }

    /**
     * 停止控制台输入输出
     */
    public void stop() {
        // 提交输入信息
        submitInput();
        // 停止系统输出输入代理
        turnOffSystemOutputProxy();
        turnOffSystemInputProxy();
        // 停止输出信息传递 Handler
        mIsRunning.set(false);
    }


    /**
     * 开启系统输出代理
     */
    private void turnOnSystemOutputProxy() {
        Application app = Utils.getApp();
        if (app instanceof JavaEngineApplication) {
            JavaEngineApplication application = (JavaEngineApplication) app;
            application.addStdErr(mErrorStream);
            application.addStdOut(mOutputStream);
            return;
        }
        throw new RuntimeException("请将您的 Application 继承于 JavaEngineApplication");
    }

    /**
     * 关闭系统输出代理
     */
    private void turnOffSystemOutputProxy() {
        Application app = Utils.getApp();
        if (app instanceof JavaEngineApplication) {
            JavaEngineApplication application = (JavaEngineApplication) app;
            application.removeStdErr(mErrorStream);
            application.removeStdOut(mOutputStream);
            return;
        }
        throw new RuntimeException("请将您的 Application 继承于 JavaEngineApplication");
    }

    /**
     * 开启系统输入代理
     */
    private void turnOnSystemInputProxy() {
        mInputBuffer.reset();
        System.setIn(mInputStream);
    }

    /**
     * 关闭系统输入代理
     */
    private void turnOffSystemInputProxy() {
        System.setIn(System.in);
    }

    public interface AppendStdListener {
        void printStderr(final CharSequence err);

        void printStdout(final CharSequence out);
    }

    public interface StdListener {
        void onUpdate();
    }
}
