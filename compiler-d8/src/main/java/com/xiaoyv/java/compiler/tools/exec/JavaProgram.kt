package com.xiaoyv.java.compiler.tools.exec

import com.xiaoyv.java.compiler.JavaEngineSetting
import com.xiaoyv.java.compiler.exception.CompileException
import dalvik.system.DexClassLoader
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * JavaProgram
 *
 * @author why
 * @since 2022/3/8
 */
class JavaProgram {

    /**
     * 运行 Dex 文件
     *
     * ### [chooseMainClassToRun]
     * - 第一个回调参数为查询到的包含 main(String[] args) 方法的所有类全路径
     * - 第二个回调参数为 [CancellableContinuation] ，将选取的类名通过 `continuation.resume()` 方法回调。
     *
     * > 若长时间未回调选择结果，协程则会一直挂起，占用资源。请及时回调或者取消 `continuation.cancel()`
     *
     * @param dexFile 文件路径
     * @param args 文件参数
     * @param chooseMainClassToRun 选取一个主类进行运行
     */
    suspend fun run(
        dexFile: String,
        args: Array<String> = emptyArray(),
        chooseMainClassToRun: (List<String>, CancellableContinuation<String>) -> Unit = { mainFunClasses, continuation ->
            if (mainFunClasses.isEmpty()) {
                continuation.resumeWithException(CompileException("未找到包含 main(String[] args) 方法的可执行类"))
            } else {
                continuation.resume(mainFunClasses.first())
            }
        }
    ) = run(File(dexFile), args, chooseMainClassToRun)

    suspend fun run(
        dexFile: File,
        args: Array<String> = emptyArray(),
        chooseMainClassToRun: (List<String>, CancellableContinuation<String>) -> Unit
    ) = withContext(Dispatchers.IO) {

        // 包含的全部 Main 方法
        val mainFunctionList = JavaProgramHelper.queryMainFunctionList(dexFile)

        System.err.println("Run-${mainFunctionList.size}，please choose!")

        // 选者的 Fun
        val mainClass = suspendCancellableCoroutine<String> {
            launch(Dispatchers.Main) {
                chooseMainClassToRun.invoke(mainFunctionList, it)
            }
        }

        val optimizedDirectory = JavaEngineSetting.defaultCacheDir
        val dexClassLoader = DexClassLoader(
            dexFile.absolutePath, optimizedDirectory, null, ClassLoader.getSystemClassLoader()
        )

        // 加载 Class
        val clazz = dexClassLoader.loadClass(mainClass)
        // 获取 main 方法
        val method = clazz.getDeclaredMethod("main", Array<String>::class.java)
        // 调用静态方法可以直接传 null
        method.invoke(null, args) ?: 0
    }
}