package com.xiaoyv.javaengine

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.blankj.utilcode.util.ToastUtils
import com.xiaoyv.java.compiler.JavaEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var toolbar: Toolbar
    private lateinit var tvPrint: TextView
    private lateinit var tvClose: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setSupportActionBar(toolbar)
        initEvent()
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        tvPrint = findViewById(R.id.tv_print)
        tvClose = findViewById(R.id.tv_close)
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

            val handle = JavaEngine.javaProgram.run(dexFile,
                printOut = {
                    tvPrint.append(it)
                },
                printErr = {
                    tvPrint.append(it)
                })

            LogUtils.e("main 执行完成, current thread: ${Thread.currentThread().name}")

            tvClose.setOnClickListener {
                handle.close()
            }

            toolbar.setOnClickListener {
                System.err.println("xxxxxxxxxxxx")
                ToastUtils.showShort("关闭")
            }
        }
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
                "        System.out.println(\"Hello 0000\");\n" +
                "        try { Thread.sleep(2000); }catch(Exception e){}" +
                "      Thread t=  new Thread(()->{ " +
                "try { Thread.sleep(2000); }catch(Exception e){}" +
                "System.out.println(\"Hello 3333\");" +
                "System.err.println(\"Hello 4444\");" +
                "System.out.println(\"Hello 5555\");});" +
                "t.start();     " +
//                "try {    t.join();}catch(Exception e){}" +
                "System.err.println(\"Hello 第一名!\");" +
                "System.out.println(\"Hello 2222!\");\n" +
                "    }\n" +
                "}"

    }
}