@file:Suppress("MemberVisibilityCanBePrivate")

package com.xiaoyv.java.compiler.tools.dex

import com.android.tools.r8.D8
import com.android.tools.r8.D8Command
import com.android.tools.r8.origin.Origin
import com.xiaoyv.java.compiler.JavaEngine
import com.xiaoyv.java.compiler.JavaEngineSetting
import com.xiaoyv.java.compiler.exception.CompileException
import com.xiaoyv.java.compiler.utils.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * JavaDexCompiler
 *
 * @author why
 * @since 2022/3/7
 */
class JavaDexCompiler {
    /**
     * 根据文件路径执行编译操作
     *
     * @param sourceFile       待编译文件，可以是 *.zip，*.jar，*.class
     * @param buildDir         build Dir
     */
    suspend fun compile(
        sourceFile: String,
        buildDir: String
    ): File = compile(File(sourceFile), File(buildDir))

    /**
     * 根据文件对象执行编译操作
     *
     * @param sourceFile  待编译文件，可以是 *.zip，*.jar，*.class
     * @param buildDir    build Dir
     */
    suspend fun compile(
        sourceFile: File,
        buildDir: File
    ): File = compile(arrayListOf(sourceFile), buildDir)

    /**
     * 根据文件路径执行编译操作
     *
     * @param sourceFileList  待编译文件或文件夹的整体集合，内容可以是 *.zip，*.jar，*.class
     * @param buildDir         build Dir
     */
    suspend fun compile(
        sourceFileList: List<String>,
        buildDir: String
    ): File = compile(
        sourceFileList.map { File(it) }.filter { it.exists() && it.isFile }, File(buildDir)
    )

    /**
     * 根据文件对象执行编译操作
     *
     * @param sourceFileList  待编译文件或文件夹的整体集合，内容可以是 *.zip，*.jar，*.class
     * @param buildDir         build Dir
     */
    suspend fun compile(
        sourceFileList: List<File>,
        buildDir: File
    ): File = withContext(Dispatchers.IO) {

        if (sourceFileList.isEmpty()) {
            throw CompileException("待编译源文件不存在")
        }

        // Dex.jar 保存文件夹
        val buildDexDir = buildDexDir(buildDir)

        // 遍历待编译的的 class 相关包或文件
        val sourceFiles = StringBuilder().apply {
            sourceFileList.filter {
                val isArchive = it.name.endsWith("class") ||
                        it.name.endsWith("jar") ||
                        it.name.endsWith("zip")
                it.exists() && it.isFile && isArchive
            }.forEach {
                append(it.absolutePath)
                append(" ")
            }
        }

        // 编译命令
        val compileCmd = arrayListOf(
            "--lib", JavaEngine.compilerSetting.rtPath,
            "--output", buildDexDir.absolutePath,
            "--release",
            sourceFiles.toString()
        ).toTypedArray()

        // 编译命令
        JavaEngine.logInfo("编译命令: \n ${compileCmd.joinToString(" ")}")

        // 编译
        D8.run(D8Command.parse(compileCmd, Origin.root(), JavaDexDiagnosticsHandler()).build())

        val classesDex = buildDexDir.absolutePath + File.separator + "classes.dex"
        if (FileUtils.isFileExists(classesDex).not()) {
            throw CompileException("编译失败，目标文件未找到：$classesDex")
        }

        return@withContext File(classesDex)
    }


    /**
     * Dex 保存文件夹
     */
    private fun buildDexDir(buildClassesDir: File) =
        File(buildClassesDir.absolutePath + File.separator + "dex").also {
            JavaEngineSetting.createAndCleanDir(it)
        }

}