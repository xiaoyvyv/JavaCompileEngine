package com.xiaoyv.javaengine

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.xiaoyv.java.compiler.JavaEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File

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

        val main1 =
            File("/storage/emulated/0/Android/data/com.xiaoyv.javaengine/files/java/com/Main1.java")
        LogUtils.e(main1.readText())

        val javaDir = File(PathUtils.getExternalAppFilesPath() + "/java")
        val javaFile = File(PathUtils.getExternalAppFilesPath() + "/java/Main.java")
        val saveDir = File(PathUtils.getExternalAppFilesPath() + "/build")
        FileIOUtils.writeFileFromString(javaFile, testJava)

        LogUtils.e("编译开始")

        launch(JavaEngine.CompileExceptionHandler) {
            val compile: File = JavaEngine.classCompiler.compile(
                javaDir,
                saveDir,
                null,
                compileProgress = { task, progress ->
                    LogUtils.e("Task:$task progress: $progress")
                })
            LogUtils.e("编译结束: $compile")
        }

        LogUtils.e("编译结束")


//        ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Boolean>() {
//            @Override
//            public Boolean doInBackground() throws Throwable {
//                String classFile = PathUtils.getExternalAppFilesPath() + "/Main.class";
//                LogUtils.e("start", classFile);
//
//
//                String[] command = new String[]{classFile, "--lib", "JavaEngineSetting.getRtPath()",
//                        "--output", PathUtils.getExternalAppFilesPath()};
//                D8.main(command);
//                return null;
//            }
//
//            @Override
//            public void onSuccess(Boolean result) {
//                LogUtils.e("success");
//            }
//        });
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