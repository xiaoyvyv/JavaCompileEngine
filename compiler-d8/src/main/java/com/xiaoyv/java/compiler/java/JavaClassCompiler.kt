@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.java.compiler.java

import com.xiaoyv.java.compiler.JavaEngine
import com.xiaoyv.java.compiler.JavaPrintWriter
import com.xiaoyv.java.compiler.exception.CompileException
import com.xiaoyv.java.compiler.listener.CompileProgress
import com.xiaoyv.java.compiler.utils.ClassUtils
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
     * 根据文件对象执行编译操作
     *
     * @param sourceFileOrDir  待编译文件或文件夹
     * @param saveFolder       编译完成的Class文件存放文件夹
     * @param libFolder        存放依赖的文件夹
     */
    @JvmOverloads
    suspend fun compile(
        sourceFileOrDir: File,
        saveFolder: File,
        libFolder: File? = null,
        compileProgress: (String, Int) -> Unit = { _, _ -> }
    ): File = withContext(Dispatchers.IO) {
        // 待编译文件
        if (sourceFileOrDir.exists().not()) {
            throw CompileException("编译代码源不存在")
        }

        // 创建编译文件保存目录
        val existsDir = FileUtils.createOrExistsDir(saveFolder)

        // 权限错误
        if (!existsDir) {
            throw CompileException("无法创建文件夹以保存编译的类文件，请检查权限：" + saveFolder.absolutePath)
        }

        // 删除已经存在的内容
        FileUtils.deleteAllInDir(saveFolder)

        // 添加依赖的全部类路径
        var libClassPath = ""
        if (libFolder != null) {
            if (!FileUtils.createOrExistsDir(libFolder)) {
                throw CompileException("无法读取依赖存放文件夹，请检查权限：" + libFolder.absolutePath)
            }
            libClassPath = ClassUtils.getLibClassPath(libFolder)
        }

        // 类路径（依赖文件路径，多个用 File.pathSeparator 分隔开）
        val classPath = libClassPath + File.pathSeparator + JavaEngine.compilerSetting.rtPath

        JavaEngine.logInfo("编译的依赖类路径：$classPath")

        // 编译命令
        val compileCmd = arrayListOf(
            sourceFileOrDir.absolutePath,
            "-d", saveFolder.absolutePath,
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
            object : CompileProgress() {
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
            queryClassFile(sourceFileOrDir, saveFolder)
        }
        // 编译文件夹的情况，返回 jar 路径
        else {
            queryJarFile(sourceFileOrDir, saveFolder)
        }
    }

    /**
     * 查询生成的单个类文件
     */
    private fun queryClassFile(sourceFile: File, saveFolder: File): File {
        val source = sourceFile.absolutePath
        val classFileName = FileUtils.getFileNameNoExtension(source) + ".class"

        // 检测源码单文件是否含有 package xxx.xxx;
        val codeStr = sourceFile.readText()

        // 返回文件路径（Class文件路径）
        var classFilePath: String = saveFolder.absolutePath + File.separator + classFileName

        val packageKeyWord = "package"

        if (codeStr.contains(packageKeyWord)) {
            runCatching {
                var packageName = codeStr.substring(
                    codeStr.indexOf("package") + packageKeyWord.length,
                    codeStr.indexOf(";")
                )
                packageName = packageName.replace(" ", "")
                packageName = packageName.replace(".", "/")

                classFilePath = saveFolder.absolutePath +
                        File.separator + packageName +
                        File.separator + classFileName
            }
        }

        return File(classFilePath)
    }

    /**
     * 查询生成的多个类文件打包 Jar
     */
    private fun queryJarFile(sourceDir: File, saveFolder: File): File {
        // 将编译后的项目打包为jar
        // 获取 bin 文件夹内的文件和包
        val fileList = FileUtils.listFilesInDir(saveFolder)
        val jarPath = saveFolder.toString() +
                File.separator + FileUtils.getFileNameNoExtension(sourceDir) + ".jar"

        // 清除缓存
        FileUtils.createFileByDeleteOldFile(jarPath)

        // 打包 jar
        val jarFile = File(jarPath)
        if (ZipUtils.zipFiles(fileList, jarFile)) {
            // 返回编译完成的 jar文件路径
            return jarFile
        }

        throw CompileException("Jar 打包错误：权限错误或未知错误")
    }
}