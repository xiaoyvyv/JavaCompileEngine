package com.xiaoyv.javaengine.compile.listener;


import android.util.Log;

import org.eclipse.jdt.core.compiler.CompilationProgress;

/**
 * java 文件编译进度类
 */
public abstract class CompileProgress extends CompilationProgress {
    /**
     * 编译总数目
     */
    private int allSize = 0;

    /**
     * 编译完成数目
     */
    private int index = 0;

    @Override
    public void begin(int i) {
        allSize = i;
        index = 0;
        Log.e("编译进度", "待编译文件数目：" + i);
    }

    @Override
    public void done() {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public void setTaskName(String s) {
        int progress = (int) ((index / (allSize * 1.0f) * 100));
        onProgress(s, progress);
        Log.e("编译进度", "当前编译第" + index + "个  文件名：" + s + "  总进度：" + progress);
        index++;
    }

    @Override
    public void worked(int workIncrement, int remainingWork) {

    }

    protected abstract void onProgress(String task, int progress);
}
