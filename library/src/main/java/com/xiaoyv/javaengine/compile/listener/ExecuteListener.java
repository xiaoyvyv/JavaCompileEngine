package com.xiaoyv.javaengine.compile.listener;


/**
 * Dex 文件执行结果监听
 */
public interface ExecuteListener {
    /**
     * 执行完成
     */
    void onExecuteFinish();

    /**
     * 执行错误
     *
     * @param error 错误信息
     */
    void onExecuteError(Throwable error);
}
