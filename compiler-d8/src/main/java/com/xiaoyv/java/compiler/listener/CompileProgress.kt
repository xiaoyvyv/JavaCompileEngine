@file:Suppress("MemberVisibilityCanBePrivate")

package com.xiaoyv.java.compiler.listener

import android.util.Log
import org.eclipse.jdt.core.compiler.CompilationProgress

/**
 * CompileProgress
 *
 * @author why
 * @since 2022/3/7
 */
abstract class CompileProgress : CompilationProgress() {
    /**
     * 编译总数目
     */
    private var allSize = 0

    /**
     * 编译完成数目
     */
    private var index = 0

    /**
     * 设置是否取消
     */
    var canceled = false

    override fun begin(remainingWork: Int) {
        allSize = remainingWork
        index = 0
    }

    override fun done() {

    }

    override fun isCanceled(): Boolean {
        return canceled
    }

    override fun setTaskName(name: String) {
        val progress = (index / (allSize * 1.0f) * 100).toInt()
        onProgress(name, progress)
        index++
    }

    override fun worked(workIncrement: Int, remainingWork: Int) {

    }

    abstract fun onProgress(task: String, progress: Int)
}