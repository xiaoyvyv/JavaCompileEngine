# JavaCompileEngine
[![](https://jitpack.io/v/xiaoyvyv/JavaCompileEngine.svg)](https://jitpack.io/#xiaoyvyv/JavaCompileEngine)
> 这是一个能够在Android上面编译和运行Java代码的引擎，其原理为将Java代码编译为class文件。
> 再将class文件转换为dex文件（dex是Android平台上(Dalvik虚拟机)的可执行文件）。
> 最后再运行dex文件，并且代理系统的输入和输出。


## 示例下载
[JavaEngineDemo](app_image/demo.apk?raw=true)

## 截图预览
|   |   |   |
|:--|:--|:--|
|  ![截图预览](app_image/1.jpg?raw=true) |![截图预览](app_image/2.jpg?raw=true)   | ![截图预览](app_image/3.jpg?raw=true)  |

## 1.安装
第一步：在`Project`的`build.gradle`内添加`jitpack.io`仓库
```kotlin
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
第二步：添加依赖
```kotlin
    dependencies {
        ...
        // 两个都是必须，dx这个引用是转换dex文件相关
        implementation 'com.github.xiaoyvyv.JavaCompileEngine:library:1.1'
        implementation 'com.github.xiaoyvyv.JavaCompileEngine:dx:1.1'
        ...
    }
```

## 2.继承 JavaEngineAplication
将你的`Application`修改为继承于`JavaEngineAplication`
```java
public class YourApplication extends JavaEngineApplication {

}
```
将其并设置到`manifests`
```xml
    <application
        android:name=".YourApplication"
        android:allowBackup="false"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme.NoActionBar">
        ...
    </application>
```
## 3.创建JavaConsole
`JavaConsole`用于代理系统输入输出
```java
    private TextView printView;

    /**
     * Java控制台对象
     */
    private JavaConsole javaConsole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ...
        printView = findViewById(R.id.printView);
        ...
        
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
```

## 4.编译 Java文件
`ClassCompiler`提供了相关的方法，具体请查阅 [ClassCompiler.java](https://github.com/xiaoyvyv/JavaCompileEngine/blob/master/library/src/main/java/com/xiaoyv/javaengine/compile/ClassCompiler.java)

```java
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
```
## 5.转换 class文件 到 dex文件
`DexCompiler`提供了相关的方法，具体请查阅 [DexCompiler.java](https://github.com/xiaoyvyv/JavaCompileEngine/blob/master/library/src/main/java/com/xiaoyv/javaengine/compile/DexCompiler.java)

```java
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
```
## 6.运行 dex文件
运行 Dex文件前，必须开启控制台`javaConsole.start()`，程序执行完成后必须关闭控制台`javaConsole.stop()`。程序运行中时禁止重复运行（如，程序等待用户输入时会挂起，此时严禁再次运行程序，需要完成挂起的程序方可继续运行新程序）
`DexExecutor`提供了相关的方法，具体请查阅 [DexExecutor.java](https://github.com/xiaoyvyv/JavaCompileEngine/blob/master/library/src/main/java/com/xiaoyv/javaengine/executor/DexExecutor.java)
```java
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
```
## 7.输入数据 如 Scanner等等的处理
直接调用`    JavaConsole.`的`inputStdin(String stdin)`方法即可输入数据。
```java
    javaConsole.inputStdin(str);
```

## 8.问题
更多内容请查阅 [Demo](https://github.com/xiaoyvyv/JavaCompileEngine/tree/master/app) 和源码 [library](https://github.com/xiaoyvyv/JavaCompileEngine/tree/master/library/)

## 9.反馈
QQEmail：1223414335@qq.com



