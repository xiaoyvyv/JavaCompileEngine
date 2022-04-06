package com.xiaoyv.java.compiler.tools.java

import android.util.Log
import com.xiaoyv.java.compiler.utils.FileUtils
import java.io.File
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile

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

    @JvmStatic
    fun getClasses(jarPath: String) = getClasses(File(jarPath))

    @JvmStatic
    fun getClasses(file: File): List<Class<*>> {
        if (file.exists().not()) {
            return arrayListOf()
        }
        val classes: MutableList<Class<*>> = arrayListOf()
        runCatching {
            val jarFile = JarFile(file)
            val entries: Enumeration<JarEntry> = jarFile.entries()
            while (entries.hasMoreElements()) {
                val jarEntry: JarEntry = entries.nextElement()
                if (jarEntry.isDirectory ||
                    jarEntry.name.endsWith(".class").not() ||
                    jarEntry.name.contains("$")
                ) {
                    continue
                }
                var className = jarEntry.name.substring(0, jarEntry.name.length - 6)
                className = className.replace('/', '.')
                runCatching {
                    classes.add(ClassLoader.getSystemClassLoader().loadClass(className))
                }.onFailure {
                    Log.e("JavaClassHelper", "编译器未适配该类： $className")
                }
            }
        }
        return classes
    }
}