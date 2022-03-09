@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.xiaoyv.java.compiler

import android.graphics.Color
import androidx.annotation.ColorInt
import com.android.tools.r8.DiagnosticsLevel
import com.xiaoyv.java.compiler.utils.FileUtils
import com.xiaoyv.java.compiler.utils.GlobalUtils
import com.xiaoyv.java.compiler.utils.SPUtils
import java.io.File

class JavaEngineSetting {

    /**
     * 设置相关的 SP
     */
    private val setting: SPUtils = SPUtils.getInstance(SETTING_KEY)

    /**
     * 恢复默认配置
     */
    fun restore() {
        setting.put(SP_KEY_RT_PATH, defaultRtJar)
        setting.put(SP_KEY_COMPILE_SOURCE, DEFAULT_SOURCE_VERSION)
        setting.put(SP_KEY_COMPILE_TARGET, DEFAULT_TARGET_VERSION)
        setting.put(SP_KEY_COMPILE_ENCODING, DEFAULT_COMPILE_CHARSET)
        setting.put(SP_KEY_COMPILE_VERBOSE, false)
        setting.put(SP_KEY_RUN_ARGS, "")
    }

    /**
     * 删除旧日志并创建空白日志文件（用于储存每次编译的信息）
     */
    fun restLog(): String {
        createAndCleanFile(logFilePath)
        return logFilePath
    }

    /**
     * Java runtime jar
     */
    var rtPath: String
        set(value) = setting.put(SP_KEY_RT_PATH, value.ifEmpty { defaultRtJar })
        get() {
            val path = setting.getString(SP_KEY_RT_PATH)
            return path.ifEmpty { defaultRtJar }
        }

    /**
     * 编译源码版本
     */
    var classSourceVersion: String
        set(value) = setting.put(SP_KEY_COMPILE_SOURCE, value.ifEmpty { DEFAULT_SOURCE_VERSION })
        get() = setting.getString(SP_KEY_COMPILE_SOURCE).ifEmpty { DEFAULT_SOURCE_VERSION }

    /**
     * 编译目标版本
     */
    var classTargetVersion: String
        set(value) = setting.put(SP_KEY_COMPILE_TARGET, value.ifEmpty { DEFAULT_TARGET_VERSION })
        get() = setting.getString(SP_KEY_COMPILE_TARGET).ifEmpty { DEFAULT_TARGET_VERSION }

    /**
     * 编译编码
     */
    var compileEncoding: String
        set(value) = setting.put(SP_KEY_COMPILE_ENCODING, value.ifEmpty { DEFAULT_COMPILE_CHARSET })
        get() = setting.getString(SP_KEY_COMPILE_ENCODING).ifEmpty { DEFAULT_COMPILE_CHARSET }

    /**
     * 编译详细
     */
    var isClassVerbose: Boolean
        set(value) = setting.put(SP_KEY_COMPILE_VERBOSE, value)
        get() = setting.getBoolean(SP_KEY_COMPILE_VERBOSE, false)

    /**
     * 运行参数
     */
    var mainArgs: String
        set(value) = setting.put(SP_KEY_RUN_ARGS, value.ifEmpty { "" })
        get() = setting.getString(SP_KEY_RUN_ARGS).ifEmpty { "" }

    /**
     * 运行参数
     */
    var dexLogLevel: String
        set(value) = setting.put(SP_KEY_COMPILE_DEX_LOG_LEVEL, value.ifEmpty {
            DiagnosticsLevel.INFO.name
        })
        get() = setting.getString(SP_KEY_COMPILE_DEX_LOG_LEVEL).ifEmpty {
            DiagnosticsLevel.INFO.name
        }

    /**
     * 控制台输出颜色
     */
    var normalLogColor: Int
        set(@ColorInt value) = setting.put(SP_KEY_CONSOLE_COLOR_OUTPUT, value)
        @ColorInt get() = setting.getInt(SP_KEY_CONSOLE_COLOR_OUTPUT, Color.WHITE)

    /**
     * 控制台错误颜色
     */
    var errorLogColor: Int
        set(@ColorInt value) = setting.put(SP_KEY_CONSOLE_COLOR_ERROR, value)
        @ColorInt get() = setting.getInt(SP_KEY_CONSOLE_COLOR_ERROR, Color.RED)

    companion object {
        private const val SETTING_KEY = "JavaSetting"
        private const val DEFAULT_SOURCE_VERSION = "1.8"
        private const val DEFAULT_TARGET_VERSION = "1.8"
        private const val DEFAULT_COMPILE_CHARSET = "UTF-8"

        /**
         * SP 相关
         */
        private const val SP_KEY_RT_PATH = "rt_path"
        private const val SP_KEY_COMPILE_SOURCE = "compile_source"
        private const val SP_KEY_COMPILE_TARGET = "compile_target"
        private const val SP_KEY_COMPILE_ENCODING = "compile_encoding"
        private const val SP_KEY_COMPILE_VERBOSE = "compile_verbose"
        private const val SP_KEY_RUN_ARGS = "run_args"

        private const val SP_KEY_COMPILE_DEX_LOG_LEVEL = "compile_dex_log_level"
        private const val SP_KEY_CONSOLE_COLOR_OUTPUT = "console_color_out"
        private const val SP_KEY_CONSOLE_COLOR_ERROR = "console_color_err"

        /**
         * 默认的 rt.jar 路径
         */
        private val defaultRtJar: String
            get() = GlobalUtils.getApp().filesDir.absolutePath + "/lib/rt.jar"

        /**
         * 默认缓存路径
         */
        val defaultCacheDir: String
            get() = createAndCleanDir(GlobalUtils.getApp().filesDir.absolutePath + "/tmp/compiler")

        /**
         * 编译日志文件保存路径
         */
        val logFilePath: String
            get() = GlobalUtils.getApp().cacheDir.absolutePath +
                    File.separator + "class_compile.log"

        /**
         * 创建并清空目标文件夹
         */
        fun createAndCleanDir(dirPath: String) = createAndCleanDir(File(dirPath))
        fun createAndCleanDir(dir: File): String {
            FileUtils.createOrExistsDir(dir)
            FileUtils.deleteAllInDir(dir)
            return dir.absolutePath
        }

        /**
         * 创建并清空目标文件
         */
        fun createAndCleanFile(filePath: String) = createAndCleanFile(File(filePath))
        fun createAndCleanFile(file: File): String {
            FileUtils.createFileByDeleteOldFile(file)
            return file.absolutePath
        }
    }
}