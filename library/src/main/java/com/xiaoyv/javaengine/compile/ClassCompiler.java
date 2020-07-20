package com.xiaoyv.javaengine.compile;

import android.util.Log;

import androidx.annotation.NonNull;

import com.xiaoyv.javaengine.JavaEngineSetting;
import com.xiaoyv.javaengine.compile.listener.CompilerListener;
import com.xiaoyv.javaengine.compile.listener.CompileProgress;
import com.xiaoyv.javaengine.executor.helper.ClassHelper;
import com.xiaoyv.javaengine.utils.FileIOUtils;
import com.xiaoyv.javaengine.utils.FileUtils;
import com.xiaoyv.javaengine.utils.ThreadUtils;
import com.xiaoyv.javaengine.utils.Utils;
import com.xiaoyv.javaengine.utils.ZipUtils;

import org.eclipse.jdt.internal.compiler.batch.Main;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Java编译类
 * <p>
 * 将java文件编译为class文件
 */
public class ClassCompiler {

    private static final String LogFile = "class_compile.log";

    /**
     * 根据文件路径执行编译操作（没有依赖文件时）
     *
     * @param sourceFileOrDirPath 待编译文件或文件夹的路径
     * @param saveFolderPath      编译完成的Class文件存放文件夹的路径
     * @param compilerListener    编译进度监听器
     */
    public void compile(@NonNull final String sourceFileOrDirPath, @NonNull final String saveFolderPath, @NonNull final CompilerListener compilerListener) {
        File sourceFileOrDir = new File(sourceFileOrDirPath);
        File saveFolder = new File(saveFolderPath);
        compile(sourceFileOrDir, saveFolder, null, compilerListener);
    }

    /**
     * 根据文件对象执行编译操作（没有依赖文件时）
     *
     * @param sourceFileOrDir  待编译文件或文件夹
     * @param saveFolder       编译完成的Class文件存放文件夹
     * @param compilerListener 编译进度监听器
     */
    public void compile(@NonNull final File sourceFileOrDir, @NonNull final File saveFolder, @NonNull final CompilerListener compilerListener) {
        compile(sourceFileOrDir, saveFolder, null, compilerListener);
    }

    /**
     * 根据文件路径执行编译操作
     *
     * @param sourceFileOrDirPath 待编译文件或文件夹的路径
     * @param saveFolderPath      编译完成的Class文件存放文件夹的路径
     * @param libFolderPath       存放依赖文件的文件夹路径
     * @param compilerListener    编译进度监听器
     */
    public void compile(@NonNull final String sourceFileOrDirPath, @NonNull final String saveFolderPath, final String libFolderPath, @NonNull final CompilerListener compilerListener) {
        File sourceFileOrDir = new File(sourceFileOrDirPath);
        File saveFolder = new File(saveFolderPath);
        File libFolder = new File(libFolderPath);
        compile(sourceFileOrDir, saveFolder, libFolder, compilerListener);
    }

    /**
     * 根据文件对象执行编译操作
     *
     * @param sourceFileOrDir  待编译文件或文件夹
     * @param saveFolder       编译完成的Class文件存放文件夹
     * @param libFolder        存放依赖的文件夹
     * @param compilerListener 编译进度监听器
     */
    public void compile(@NonNull final File sourceFileOrDir, @NonNull final File saveFolder, final File libFolder, @NonNull final CompilerListener compilerListener) {

        // 在线程中执行编译操作
        ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Object>() {
            @Override
            public Object doInBackground() throws Throwable {
                // 待编译文件
                if (!sourceFileOrDir.exists()) {
                    throw new Exception("编译代码源不存在");
                }

                // 创建编译文件保存目录
                boolean existsDir = FileUtils.createOrExistsDir(saveFolder);
                // 权限错误
                if (!existsDir) {
                    throw new Exception("无法创建文件夹以保存编译的类文件，请检查权限：" + saveFolder.getAbsolutePath());
                }
                // 删除已经存在的内容
                FileUtils.deleteAllInDir(saveFolder);


                // 添加依赖的全部类路径
                String libClassPath = "";
                if (libFolder != null) {
                    if (!FileUtils.createOrExistsDir(libFolder)) {
                        throw new Exception("无法读取依赖存放文件夹，请检查权限：" + libFolder.getAbsolutePath());
                    }
                    libClassPath = ClassHelper.getLibClassPath(libFolder);
                }

                // 类路径（依赖文件路径，多个用 File.pathSeparator 分隔开）
                String classPath = libClassPath + File.pathSeparator + JavaEngineSetting.getRtPath();

                Log.i("编译的依赖类路径", classPath);

                // 编译命令
                String[] compileCmd = new String[]{
                        sourceFileOrDir.getAbsolutePath(),
                        "-d", saveFolder.getAbsolutePath(),
                        "-encoding", JavaEngineSetting.getCompileEncoding(),
                        "-classpath", classPath,
                        "-source", JavaEngineSetting.getClassSourceVersion(),
                        "-target", JavaEngineSetting.getClassTargetVersion(),
                        // JavaCompileEngineSetting.isClassVerbose() ? "-verbose" : "",
                        "-nowarn",
                        "-time"
                };

                Log.i("编译命令", Arrays.toString(compileCmd));

                // 编译日志文件保存路径
                String logFilePath = Utils.getApp().getCacheDir() + File.separator + LogFile;

                // 删除旧日志并创建空白日志文件（用于储存每次编译的信息）
                FileUtils.createFileByDeleteOldFile(logFilePath);

                // 日志输出
                PrintWriter printWriter = new PrintWriter(logFilePath);

                // 开始编译
                boolean compile = Main.compile(compileCmd, printWriter, printWriter, new CompileProgress() {
                    @Override
                    protected void onProgress(final String task, final int progress) {
                        // 编译文件监听，UI线程回调正在编译文件名和进度
                        Utils.runOnUiThread(() ->
                                compilerListener.onProgress(task, progress));
                    }
                });

                // 编译结果
                if (compile) {
                    // 编译完成，若编译的为文件，返回class类文件的路径，若编译的文件夹，返回jar路径
                    if (FileUtils.isFile(sourceFileOrDir)) {
                        String source = sourceFileOrDir.getAbsolutePath();
                        String classFileName = FileUtils.getFileNameNoExtension(source) + ".class";
                        // 检测源码单文件是否含有 package xxx.xxx;
                        String codeStr = FileIOUtils.readFile2String(sourceFileOrDir);

                        // 返回文件路径（Class文件路径）
                        String classFilePath = saveFolder.getAbsolutePath() + "/" + classFileName;

                        if (codeStr.contains("package")) {
                            try {
                                String packageName = codeStr.substring(codeStr.indexOf("package") + 7, codeStr.indexOf(";"));
                                packageName = packageName.replace(" ", "");
                                packageName = packageName.replace(".", "/");

                                classFilePath = saveFolder.getAbsolutePath() + "/" + packageName + "/" + classFileName;
                                return classFilePath;
                            } catch (Exception e) {
                                return classFilePath;
                            }
                        } else return classFilePath;
                    }
                    // 当编译的是文件夹项目时
                    else {
                        // 将编译后的项目打包为jar
                        // 获取 bin 文件夹内的文件和包
                        List<File> fileList = FileUtils.listFilesInDir(saveFolder);
                        File jarPath = new File(saveFolder + "/" + FileUtils.getFileNameNoExtension(sourceFileOrDir) + ".jar");
                        // 打包jar
                        try {
                            FileUtils.createFileByDeleteOldFile(jarPath);
                            boolean b = ZipUtils.zipFiles(fileList, jarPath);
                            if (b) {
                                // 返回编译完成的 jar文件路径
                                return jarPath.getAbsolutePath();
                            } else {
                                throw new Exception("Jar打包错误：权限错误或未知错误");
                            }
                        } catch (Exception e) {
                            throw new Exception("Jar打包错误", e);
                        }
                    }
                } else {
                    // 读取错误日志
                    String log = FileIOUtils.readFile2String(logFilePath);
                    throw new Exception("字节码编译错误：\n" + log);
                }
            }

            @Override
            public void onFail(Throwable t) {
                compilerListener.onError(t);
            }

            @Override
            public void onSuccess(Object result) {
                compilerListener.onSuccess(String.valueOf(result));
            }
        });
    }

}
