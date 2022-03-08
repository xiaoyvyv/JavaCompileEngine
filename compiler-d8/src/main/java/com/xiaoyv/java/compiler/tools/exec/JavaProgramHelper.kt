@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.java.compiler.tools.exec

import com.xiaoyv.java.compiler.tools.dex.parse.Dex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * JavaProgramHelper
 *
 * @author why
 * @since 2022/3/8
 */
object JavaProgramHelper {

    /**
     * 检测含有 main 方法的类
     *
     * @param dexFile Dex文件
     * @return 返回存在 main 方法的所有类 的全路径集合
     */
    @JvmStatic
    suspend fun queryMainFunctionList(dexFile: File): List<String> = withContext(Dispatchers.IO) {
        arrayListOf<String>().apply {
            runCatching {
                val dex = Dex(dexFile)
                dex.methodIds().forEach { method ->
                    // 类路径 例如：Lpackage/Hello;
                    val declaringClass = dex.typeNames()[method.declaringClassIndex]

                    // 方法名 例如：main
                    val methodName = dex.strings()[method.nameIndex]

                    // 参数类型 例如：([Ljava/lang/String;)
                    val param = dex.readTypeList(
                        dex.protoIds()[method.protoIndex].parametersOffset
                    ).toString()

                    // 检测 Main 方法
                    if (methodName == "main" && param == "([Ljava/lang/String;)") {
                        add(
                            declaringClass.replace("/", ".")
                                .replace("L", "")
                                .replace(";", "")
                        )
                    }
                }
            }
        }
    }
}