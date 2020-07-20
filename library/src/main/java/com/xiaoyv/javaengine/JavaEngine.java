package com.xiaoyv.javaengine;

import android.app.Application;
import android.util.Log;

import com.xiaoyv.javaengine.compile.ClassCompiler;
import com.xiaoyv.javaengine.compile.DexCompiler;
import com.xiaoyv.javaengine.executor.DexExecutor;
import com.xiaoyv.javaengine.utils.FileUtils;
import com.xiaoyv.javaengine.utils.ResourceUtils;
import com.xiaoyv.javaengine.utils.Utils;

/**
 * @author 王怀玉 编译器管理类
 * @since 2020/5/10
 */
public class JavaEngine {

    /**
     * 初始化 Java编译器引擎
     */
    public static void init(Application application) {
        Utils.init(application);
        boolean b = checkRtJar();
        Log.i("checkRtJar", "Java Runtime 编译文件----复制：" + b);
    }

    /**
     * 检查 rt.jar 是否存在
     */
    public static boolean checkRtJar() {
        String rtPath = JavaEngineSetting.getRtPath();
        // 不存在时从 Assets 复制
        boolean fileExists = FileUtils.isFileExists(rtPath);
        if (!fileExists) {
            return ResourceUtils.copyFileFromAssets("rt.jar", rtPath);
        }
        return true;
    }


    /**
     * Class 编译器
     */
    private volatile static ClassCompiler classCompiler;

    public static ClassCompiler getClassCompiler() {
        if (classCompiler == null) {
            synchronized (ClassCompiler.class) {
                if (classCompiler == null) {
                    classCompiler = new ClassCompiler();
                }
            }
        }
        return classCompiler;
    }


    /**
     * Dex 编译器
     */
    private volatile static DexCompiler dexCompiler;

    public static DexCompiler getDexCompiler() {
        if (dexCompiler == null) {
            synchronized (DexCompiler.class) {
                if (dexCompiler == null) {
                    dexCompiler = new DexCompiler();
                }
            }
        }
        return dexCompiler;
    }

    /**
     * Dex 执行器
     */
    private volatile static DexExecutor dexExecutor;

    public static DexExecutor getDexExecutor() {
        if (dexExecutor == null) {
            synchronized (DexExecutor.class) {
                if (dexExecutor == null) {
                    dexExecutor = new DexExecutor();
                }
            }
        }
        return dexExecutor;
    }
}
