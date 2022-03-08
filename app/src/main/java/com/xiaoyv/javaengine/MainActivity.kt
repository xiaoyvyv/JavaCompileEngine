package com.xiaoyv.javaengine

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.xiaoyv.java.compiler.JavaEngine
import com.xiaoyv.java.compiler.exception.CompileException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.resume

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setSupportActionBar(toolbar)
        initEvent()
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
    }

    private fun initEvent() {}

    fun openSingleActivity(view: View?) {
//        Intent intent = new Intent(this, SingleSampleActivity.class);
//        startActivity(intent);
    }

    fun openGithub(view: View?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.parse("https://github.com/xiaoyvyv/JavaCompileEngine")
        startActivity(intent)
    }

    fun testR8(view: View) {
        val javaDir = File(PathUtils.getExternalAppFilesPath() + "/java")
        val buildDir = File(PathUtils.getExternalAppFilesPath() + "/build")

        val javaFile = File(PathUtils.getExternalAppFilesPath() + "/java/Main.java")
        val javaFile1 = File(PathUtils.getExternalAppFilesPath() + "/java/Main.java")
        FileIOUtils.writeFileFromString(javaFile, testJava)
        FileIOUtils.writeFileFromString(javaFile1, "package com;\n$testJava")

        LogUtils.e("编译开始")

        launch(JavaEngine.CompileExceptionHandler) {
            val jarFile = JavaEngine.classCompiler.compile(
                javaDir,
                buildDir,
                compileProgress = { task, progress ->
                    LogUtils.e("Task:$task progress: $progress")
                })

            LogUtils.e("编译结束 jarFile: $jarFile")

            val dexFile = JavaEngine.dexCompiler.compile(jarFile, buildDir)
            LogUtils.e("编译结束 dexFile: $dexFile")

            JavaEngine.javaProgram.run(dexFile, chooseMainClassToRun = { list, continuation ->
                System.err.println(list)

                val create = AlertDialog.Builder(this@MainActivity)
                    .setPositiveButton("第一个") { p0, p1 ->
                        p0.dismiss()
                        continuation.cancel(CompileException("取消"))
                    }
                    .create()
                create.show()
            })

            LogUtils.e("运行结束")
        }

        LogUtils.e("编译结束")
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }


    companion object {
        const val testJava = "\n/**\n" +
                " * @author Admin\n" +
                " */\n" +
                "public class Main {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Start Thread!\");\n" +
                "        new Thread(()-> System.out.println(\"Hello World!\")).start();\n" +
                "    }\n" +
                "}"
    }
}