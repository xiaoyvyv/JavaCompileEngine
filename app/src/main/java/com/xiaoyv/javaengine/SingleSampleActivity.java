package com.xiaoyv.javaengine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.xiaoyv.javaengine.compile.listener.CompilerListener;
import com.xiaoyv.javaengine.compile.listener.ExecuteListener;
import com.xiaoyv.javaengine.console.JavaConsole;
import com.xiaoyv.javaengine.utils.FileIOUtils;
import com.xiaoyv.javaengine.utils.StringUtils;

/**
 * 编译运行单个Java文件示例（包括输入示例）
 */
public class SingleSampleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText codeText;
    private TextInputEditText inputEditView;
    private TextView printView;

    /**
     * Java控制台对象
     */
    private JavaConsole javaConsole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_sample);
        initView();
        setSupportActionBar(toolbar);
        initEvent();

        // 标题栏返回键
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        codeText = findViewById(R.id.codeText);
        inputEditView = findViewById(R.id.inputEdit);
        printView = findViewById(R.id.printView);

    }

    private void initEvent() {
        setOutputSample();

        // 新建一个控制台对象，传入输出监听（回调为主线程）
        javaConsole = new JavaConsole(new JavaConsole.AppendStdListener() {
            @Override
            public void printStderr(CharSequence err) {
                printView.append(err);
            }

            @Override
            public void printStdout(CharSequence out) {
                printView.append(out);
            }
        });

    }

    /**
     * 标题栏菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Run").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 先检查控制台是否已经有程序运行了（如等待输入状态）
                if (javaConsole.isRunning()) {
                    ToastUtils.showShort("上一个程序正在运行，请继续");
                    return true;
                }

                compileJava();
                return true;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("输出例子（Output Example）").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                setOutputSample();
                return true;
            }
        });
        menu.add("输入例子（Input Example）").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                setInputSample();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 开始编译
     */
    private void compileJava() {

        printView.setText(null);

        // 创建Java文件，写入代码内容
        String javaFilePath = PathUtils.getExternalAppFilesPath() + "/SingleExample/Main.java";
        FileIOUtils.writeFileFromString(javaFilePath, String.valueOf(codeText.getText()));

        // 保存 Class文件 的文件夹
        String saveClassFolder = PathUtils.getExternalAppFilesPath() + "/SingleExample/Class";

        // 编译 Java 文件
        JavaEngine.getClassCompiler().compile(javaFilePath, saveClassFolder, new CompilerListener() {
            @Override
            public void onSuccess(String path) {
                LogUtils.e(path);
                // 编译成功 path 为class文件路径
                // 接下来将 class文件 转换为 Dex文件
                compileDex(path);
            }

            @Override
            public void onError(Throwable error) {
                // 编译失败
                printView.setText(Html.fromHtml("<font color=\"#F00\">" + error.toString() + "</font>"));
            }
        });

    }

    /**
     * 将 Class文件 转换为 Dex文件
     *
     * @param classFilePath class文件路径
     */
    private void compileDex(String classFilePath) {
        // 先创建 Dex空白文件（不能创建在 classFilePath 的同级或子目录）
        String dexFilePath = PathUtils.getExternalAppFilesPath() + "/SingleExample/Dex/Main.dex";

        JavaEngine.getDexCompiler().compile(classFilePath, dexFilePath, new CompilerListener() {
            @Override
            public void onSuccess(String dexPath) {
                LogUtils.e(dexPath);
                // 编译成功 path 为dex文件路径
                // 接下运行 dex文件
                runDex(dexPath);
            }

            @Override
            public void onError(Throwable error) {
                // 转换失败
                printView.setText(Html.fromHtml("<font color=\"#F00\">" + error.toString() + "</>"));
            }
        });
    }


    /**
     * 运行 Dex文件
     *
     * @param dexPath Dex文件路径
     */
    private void runDex(String dexPath) {
        // 运行 Dex文件前，必须开启控制台
        // 运行 Dex文件前，必须开启控制台
        // 运行 Dex文件前，必须开启控制台
        javaConsole.start();

        // 第二个参数为运行时，传入 main(String[] args)方法 的参数 args
        String[] args = new String[]{};
        JavaEngine.getDexExecutor().exec(dexPath, args, new ExecuteListener() {
            @Override
            public void onExecuteFinish() {
                // 运行完成，关闭控制台
                javaConsole.stop();
            }

            @Override
            public void onExecuteError(Throwable error) {
                // 运行完成，关闭控制台
                javaConsole.stop();

                // 执行出错
                printView.setText(Html.fromHtml("<font color=\"#F00\">" + error.toString() + "</>"));
            }
        });

    }

    /**
     * 向控制台发送数据
     */
    public void sendStr(View view) {
        String str = String.valueOf(inputEditView.getText());
        if (!StringUtils.isEmpty(str)) {
            javaConsole.inputStdin(str);
        }
        // 清空
        inputEditView.setText(null);
    }

    @SuppressLint("SetTextI18n")
    private void setOutputSample() {
        codeText.setText("public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello World\");\n" +
                "        System.out.println(\"This is a Java Compiler\");\n" +
                "        System.out.println(\"Countdown start\");\n" +
                "        for (int i = 5; i > 0; i--) {\n" +
                "            System.out.println(i);\n" +
                "            try {\n" +
                "                Thread.sleep(1000);\n" +
                "            } catch (InterruptedException e) {\n" +
                "                e.printStackTrace();\n" +
                "            }\n" +
                "        }\n" +
                "        System.out.println(\"I am back again\");\n" +
                "    }\n" +
                "}\n");
    }

    @SuppressLint("SetTextI18n")
    private void setInputSample() {
        codeText.setText("import java.util.Scanner;\n\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello System.in\");\n" +
                "        System.out.println(\"Please enter something\");\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "        String line = scanner.nextLine();\n" +
                "        System.out.println(\"The following is your input:\");\n" +
                "        System.out.println(line);\n" +
                "    }\n" +
                "}");
    }
}
