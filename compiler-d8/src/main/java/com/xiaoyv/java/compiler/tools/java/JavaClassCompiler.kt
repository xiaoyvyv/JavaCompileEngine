@file:Suppress("SpellCheckingInspection", "SameParameterValue")

package com.xiaoyv.java.compiler.tools.java

import com.xiaoyv.java.compiler.JavaEngine
import com.xiaoyv.java.compiler.JavaEngineSetting
import com.xiaoyv.java.compiler.JavaPrintWriter
import com.xiaoyv.java.compiler.exception.CompileException
import com.xiaoyv.java.compiler.utils.FileUtils
import com.xiaoyv.java.compiler.utils.ZipUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.eclipse.jdt.internal.compiler.batch.Main
import java.io.File

/**
 * JavaClassCompiler
 *
 * @author why
 * @since 2022/3/7
 */
class JavaClassCompiler {

    /**
     * 根据文件路径执行编译操作
     *
     * @param sourceFileOrDir  待编译文件或文件夹
     * @param buildDir         build Dir
     * @param libFolder        存放依赖的文件夹
     */
    @JvmOverloads
    suspend fun compile(
        sourceFileOrDir: String,
        buildDir: String,
        libFolder: String? = null,
        compileProgress: (String, Int) -> Unit = { _, _ -> }
    ): File {
        val libDir = libFolder?.let {
            File(it)
        }
        return compile(File(sourceFileOrDir), File(buildDir), libDir, compileProgress)
    }

    /**
     * 根据文件对象执行编译操作
     *
     * @param sourceFileOrDir  待编译文件或文件夹
     * @param buildDir         build Dir
     * @param libFolder        存放依赖的文件夹
     */
    @JvmOverloads
    suspend fun compile(
        sourceFileOrDir: File,
        buildDir: File,
        libFolder: File? = null,
        compileProgress: (String, Int) -> Unit = { _, _ -> }
    ): File = withContext(Dispatchers.IO) {
        // 待编译文件
        if (sourceFileOrDir.exists().not()) {
            throw CompileException("编译代码源不存在")
        }

        // 类文件 保存文件夹
        val buildClassesDir = buildClassesDir(buildDir)
        // Jar 保存文件夹
        val buildJarDir = buildJarDir(buildDir)

        // 添加依赖的全部类路径
        var libClassPath = ""
        if (libFolder != null) {
            if (!FileUtils.createOrExistsDir(libFolder)) {
                throw CompileException("无法读取依赖存放文件夹，请检查权限：" + libFolder.absolutePath)
            }
            libClassPath = JavaClassHelper.getLibClassPath(libFolder)
        }

        // 类路径（依赖文件路径，多个用 File.pathSeparator 分隔开）
        val classPath = libClassPath + File.pathSeparator + JavaEngine.compilerSetting.rtPath

        JavaEngine.logInfo("编译的依赖类路径：$classPath")

        // 编译命令
        val compileCmd = arrayListOf(
            sourceFileOrDir.absolutePath,
            "-d", buildClassesDir.absolutePath,
            "-encoding", JavaEngine.compilerSetting.compileEncoding,
            "-sourcepath", sourceFileOrDir.absolutePath,
            "-classpath", classPath,
            "-source", JavaEngine.compilerSetting.classSourceVersion,
            "-target", JavaEngine.compilerSetting.classTargetVersion,
            "-nowarn",
            "-time",
            "-noExit"
        ).also {
            if (JavaEngine.compilerSetting.isClassVerbose) {
                it.add("-verbose")
            }
        }.toTypedArray()

        // 编译命令
        JavaEngine.logInfo("编译命令: \n ${compileCmd.joinToString(" ")}")

        // 重置日志
        val logFilePath = JavaEngine.compilerSetting.restLog()

        // 日志输出
        val printWriter = JavaPrintWriter(logFilePath)

        // 开始编译
        val compile = Main.compile(compileCmd, printWriter, printWriter,
            object : JavaClassCompileProgress() {
                override fun onProgress(task: String, progress: Int) {
                    launch(Dispatchers.Main) {
                        compileProgress.invoke(task, progress)
                    }
                }
            })

        // 编译失败，读取错误日志
        if (compile.not()) {
            // 读取错误日志
            val log = File(logFilePath).readText()
            throw Exception("字节码编译错误：\n$log")
        }

        // 编译单个文件情况，返回 class 类文件的路径
        if (sourceFileOrDir.isFile) {
            queryClassFile(sourceFileOrDir, buildClassesDir)
        }
        // 编译文件夹的情况，返回 jar 路径
        else {
            queryJarFile(buildClassesDir, buildJarDir)
        }
    }

    /**
     * class 保存文件夹
     */
    private fun buildClassesDir(buildClassesDir: File) =
        File(buildClassesDir.absolutePath + File.separator + "classes").also {
            JavaEngineSetting.createAndCleanDir(it)
        }


    /**
     * jar 保存文件夹
     */
    private fun buildJarDir(buildClassesDir: File) =
        File(buildClassesDir.absolutePath + File.separator + "jar").also {
            JavaEngineSetting.createAndCleanDir(it)
        }

    /**
     * 查询生成的单个类文件
     */
    private fun queryClassFile(sourceFile: File, buildClassesDir: File): File {
        val source = sourceFile.absolutePath
        val classFileName = FileUtils.getFileNameNoExtension(source) + ".class"

        // 检测源码单文件是否含有 package xxx.xxx;
        val codeStr = sourceFile.readText()

        // 返回文件路径（Class文件路径）
        var classFilePath: String = buildClassesDir.absolutePath + File.separator + classFileName

        val packageKeyWord = "package"

        if (codeStr.contains(packageKeyWord)) {
            runCatching {
                var packageName = codeStr.substring(
                    codeStr.indexOf("package") + packageKeyWord.length,
                    codeStr.indexOf(";")
                )
                packageName = packageName.replace(" ", "")
                packageName = packageName.replace(".", "/")

                classFilePath = buildClassesDir.absolutePath +
                        File.separator + packageName +
                        File.separator + classFileName
            }
        }

        return File(classFilePath)
    }

    /**
     * 将编译后的项目打包为 Jar
     */
    private fun queryJarFile(buildClassesDir: File, buildJarDir: File): File {
        // 获取 build/classes/xxx 文件夹内的文件和包
        val fileList = FileUtils.listFilesInDir(buildClassesDir)

        // 构建 METE-INF
        val metaInfDir = buildClassesDir.absolutePath + File.separator + "META-INF"
        JavaEngineSetting.createAndCleanDir(metaInfDir)

        // 构建 MANIFEST.MF
        val minifest = metaInfDir + File.separator + "MANIFEST.MF"
        JavaEngineSetting.createAndCleanFile(minifest)

        // Jar 存放路径
        val jarPath = buildJarDir.absolutePath + File.separator + "classes.jar"
        JavaEngineSetting.createAndCleanFile(jarPath)

        // 打包 jar
        val jarFile = File(jarPath)
        if (ZipUtils.zipFiles(fileList, jarFile)) {
            // 返回编译完成的 jar 文件路径
            return jarFile
        }

        throw CompileException("Jar 打包错误：权限错误或未知错误")
    }
}