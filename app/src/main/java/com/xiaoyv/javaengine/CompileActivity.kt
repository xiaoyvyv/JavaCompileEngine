package com.xiaoyv.javaengine

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.PathUtils
import com.xiaoyv.java.compiler.JavaEngine
import com.xiaoyv.javaengine.databinding.ActivitySingleSampleBinding
import kotlinx.coroutines.*
import java.io.File

/**
 * CompileActivity
 *
 * @author why
 * @since 2022/3/9
 */
class CompileActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var binding: ActivitySingleSampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initEvent()

        setOutputSample()
    }

    private fun initEvent() {
        binding.toolbar.menu.add("输入示例")
            .setOnMenuItemClickListener {
                setInputSample()
                true
            }.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)

        binding.toolbar.menu.add("输出示例")
            .setOnMenuItemClickListener {
                setOutputSample()
                true
            }.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)

        binding.toolbar.menu.add("Run")
            .setOnMenuItemClickListener {
                runProgram()
                true
            }.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

    }

    private fun runProgram() = launch {
        // build 文件夹
        val buildDir = PathUtils.getExternalAppFilesPath() + "/SingleExample/build"

        // java 文件夹
        val javaDir = PathUtils.getExternalAppFilesPath() + "/SingleExample/java"

        // 待编译的 Main.java
        val javaFilePath = withContext(Dispatchers.IO) {
            // java 文件夹内 Main.java 文件，写入代码内容
            val javaFilePath = "$javaDir/Main.java"

            FileIOUtils.writeFileFromString(javaFilePath, binding.codeText.text.toString())

            // 返回源文件路径
            javaFilePath
        }

        // 编译 class，libFolder 为第三方 jar 存放目录，没有传空即可
        // 编译完成返回目标 classes.jar，内部通过协程在 IO 线程处理的
        val compileJar: File = JavaEngine.classCompiler.compile(
            sourceFileOrDir = javaFilePath,
            buildDir = buildDir,
            libFolder = null
        ) { taskName, progress ->

            // 这里是进度，回调在主线程...
        }

        // 编译 classes.dex，这一步相关的信息通过 System.xxx.print 输出
        val dexFile = JavaEngine.dexCompiler.compile(compileJar.absolutePath, buildDir)

    }


    @SuppressLint("SetTextI18n")
    private fun setOutputSample() {
        binding.codeText.setText(
            "/**\n" +
                    " * @author Admin\n" +
                    " */\n" +
                    "public class Main {\n" +
                    "\n" +
                    "    @SuppressWarnings(\"AlibabaAvoidManuallyCreateThread\")\n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(\"Start Thread!\");\n" +
                    "        new Thread(()-> System.out.println(\"Hello World!\")).start();\n" +
                    "    }\n" +
                    "}"
        )
    }

    @SuppressLint("SetTextI18n")
    private fun setInputSample() {
        binding.codeText.setText(
            "import java.util.Scanner;\n\n" +
                    "public class Main {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(\"Hello System.in\");\n" +
                    "        System.out.println(\"Please enter something\");\n" +
                    "        Scanner scanner = new Scanner(System.in);\n" +
                    "        String line = scanner.nextLine();\n" +
                    "        System.out.println(\"The following is your input:\");\n" +
                    "        System.out.println(line);\n" +
                    "    }\n" +
                    "}"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}