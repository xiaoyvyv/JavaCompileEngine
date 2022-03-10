
# JavaCompileEngine （支持 JDK8 语法）

[![Mavnen Central](https://img.shields.io/maven-central/v/io.github.xiaoyvyv/compiler-d8?label=Maven%20Central)](https://search.maven.org/search?q=io.github.xiaoyvyv%20compiler-d8)

这是一个能够在 `Android` 平台上面编译和运行 `Java` 代码的引擎，支持 `JDK8` 语法编译运行。

#### 运行原理：

> 1、将 `*.java` 文件编译为 `*.class` 类文件。
> 2、通过 `Google` 的 `R8 | D8` 工具将 `*.class` 文件编译为 `Android` 平台能够运行的 `*.dex` 文件。这一步可以脱糖，可以在安卓平台运行 `JDK` 高版本的语法糖，其内部实现基本上还是语法糖转换成了低版本兼容的代码。
> 3、将 `*.dex` 文件通过 `DexClassLoader` 加载到应用程序中，并寻找 `main` 方法反射调用。
> 4、代理系统的输入`System.in` 和输出 `System.out` 。


## 示例下载
[JavaEngineDemo](app_image/demo.apk?raw=true)

## 截图预览
|   |   |   |
|:--|:--|:--|
|  ![截图预览](app_image/1.jpg?raw=true) |![截图预览](app_image/2.jpg?raw=true)   | ![截图预览](app_image/3.jpg?raw=true)  |

## 1、引入依赖
第一步：在`Project`的`build.gradle`内添加`jitpack.io`仓库
```groovy
    allprojects {
        repositories {
            //...

            mavenCentral()
        }
    }
```
第二步：添加依赖
```groovy
    dependencies {
        //...

        // 这个依赖比较大（9M 左右），因为内部（assets）包含了一个精简的 Jre，你可以自己选择去除或精简。
        implementation 'io.github.xiaoyvyv:compiler-d8:<maven-version>'
    }
```

## 2、初始化
在你的 `Application` 的 `onCreate` 中初始化库。

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // init
        JavaEngine.init(this)
    }
}
```

## 3、编译 Java 文件
`JavaClassCompiler` 提供了相关的方法，具体请查阅 [JavaClassCompiler.kt](https://github.com/xiaoyvyv/JavaCompileEngine/blob/master-d8/compiler-d8/src/main/java/com/xiaoyv/java/compiler/tools/java/JavaClassCompiler.kt)。

> 注意该方法需要配合协程使用，在协程的作用域内调用

```kotlin
// 编译 class，libFolder 为第三方 jar 存放目录，没有传空即可
// 编译完成返回目标 classes.jar，内部通过协程在 IO 线程处理的
val compileJar: File = JavaEngine.classCompiler.compile(
    sourceFileOrDir = javaFilePath,
    buildDir = buildDir,
    libFolder = null
) { taskName, progress ->

   // 这里是进度，回调在主线程...
  }
```
## 4、转换 class文件 到 dex文件
`DexCompiler` 提供了相关的方法，具体请查阅 [JavaDexCompiler.kt](https://github.com/xiaoyvyv/JavaCompileEngine/blob/master-d8/compiler-d8/src/main/java/com/xiaoyv/java/compiler/tools/dex/JavaDexCompiler.kt)

> 注意该方法需要配合协程使用，在协程的作用域内调用

```kotlin
// 编译 classes.dex，这一步相关的信息通过 System.xxx.print 输出
val dexFile = JavaEngine.dexCompiler.compile(compileJar.absolutePath, buildDir)
```
## 5、运行 dex文件
`JavaProgram.kt` 提供了相关的方法，具体请查阅 [JavaProgram.kt](https://github.com/xiaoyvyv/JavaCompileEngine/blob/master-d8/compiler-d8/src/main/java/com/xiaoyv/java/compiler/tools/exec/JavaProgram.kt)

注意 `chooseMainClassToRun` 默认实现是选中匹配到的第一个 `main` 方法进行运行，你可以自己在该方法回调内去选择需要执行的的指定某个类。
- chooseMainClassToRun 第一个回调参数：匹配到的包含 `main` 方法的类
- chooseMainClassToRun 第二个回调参数：协程相关的回调，需要将选择的类回调回去

<font color="#ff0000">注意：chooseMainClassToRun 回调会使内部协程一直挂起，你应该及时的通过 continuation.resume() 或 resume.resumeWithException() 让内部知道处理结果，禁止忽略 continuation 的回调，否则会在后台一直占用资源</font>

`chooseMainClassToRun`、`printOut`、`printErr` 回调均在主线程，可以进行 UI 操作。

`run` 方法运行完成（并不代表程序执行完成，例如你的代码启动了其他线程）会返回一个 `programConsole` 句柄，可以用于关闭输入输出流。

> 注意该方法需要配合协程使用，在协程的作用域内调用

```kotlin
// JavaEngine.
val programConsole = JavaEngine.javaProgram.run(dexFile, arrayOf("args"),
    chooseMainClassToRun = { classes, continuation ->
        val dialog = AlertDialog.Builder(this@CompileActivity)
            .setTitle("请选择一个主函数运行")
            .setItems(classes.toTypedArray()) { p0, p1 ->
                p0.dismiss()
                continuation.resume(classes[p1])
            }
            .setCancelable(false)
            .setNegativeButton("取消") { d, v ->
                d.dismiss()
                continuation.resumeWithException(Exception("取消操作"))
            }.create()

        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
    },
    printOut = {
        binding.printView.append(it)
    },
    printErr = {
        binding.printView.append(it)
    })
```
## 6、输入数据 如 Scanner等等的处理
直接调用 `programConsole.` 的 `inputStdin(String stdin)` 方法即可输入数据。
```kotlin
    programConsole.inputStdin(str)
```
## 7、编译相关设置
`JavaEngine.compilerSetting` 提供了相关配置。[JavaEngineSetting.kt](https://github.com/xiaoyvyv/JavaCompileEngine/blob/master-d8/compiler-d8/src/main/java/com/xiaoyv/java/compiler/JavaEngineSetting.kt)

## 8、问题
更多内容请查阅 [Demo](https://github.com/xiaoyvyv/JavaCompileEngine/tree/master/app) 和源码 [compiler-d8](https://github.com/xiaoyvyv/JavaCompileEngine/tree/master/compiler-d8/)

## 9、反馈
QQEmail：1223414335@qq.com



