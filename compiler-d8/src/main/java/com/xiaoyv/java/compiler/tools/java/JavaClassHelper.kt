package com.xiaoyv.java.compiler.tools.java

import com.xiaoyv.java.compiler.utils.FileUtils
import java.io.File

/**
 * @author 王怀玉
 * @date 18/07/2017
 */
object JavaClassHelper {

    /**
     * 获取当前项目 lib 目录的 jar 文件的 CLASSPATH
     *
     * @param libFolder 当前项目添加的jar依赖文件夹
     * @return 所有依赖转成的 -classpath 格式字符串
     */
    @JvmStatic
    fun getLibClassPath(libFolder: File): String {
        val existsDir = FileUtils.createOrExistsDir(libFolder)
        if (!existsDir) {
            return ""
        }

        val javaLibraries = FileUtils.listFilesInDirWithFilter(libFolder) { file ->
            FileUtils.isFile(file) && FileUtils.getFileName(file).endsWith(".jar")
        } ?: return ""

        val classpath = StringBuilder(".")
        for (javaLibrary in javaLibraries) {
            if (classpath.isNotEmpty()) {
                classpath.append(File.pathSeparator)
            }
            classpath.append(javaLibrary.absolutePath)
        }
        return classpath.toString()
    }
}