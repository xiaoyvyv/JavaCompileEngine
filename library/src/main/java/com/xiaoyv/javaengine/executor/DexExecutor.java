package com.xiaoyv.javaengine.executor;

import androidx.annotation.NonNull;

import com.xiaoyv.dex.Dex;
import com.xiaoyv.dex.MethodId;
import com.xiaoyv.javaengine.JavaEngineSetting;
import com.xiaoyv.javaengine.compile.listener.ExecuteListener;
import com.xiaoyv.javaengine.utils.FileUtils;
import com.xiaoyv.javaengine.utils.StringUtils;
import com.xiaoyv.javaengine.utils.ThreadUtils;
import com.xiaoyv.javaengine.utils.Utils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;

/**
 * 执行dex的main方法
 */
public class DexExecutor {

    /**
     * 执行dex文件（不传入 main 方法所在类的类名全路径，自动检测到第一个为止）
     *
     * @param dexFilePath     待执行的dex文件路径
     * @param args            main方法参数
     * @param executeListener 执行监听器
     */
    public void exec(@NonNull String dexFilePath, String[] args, @NonNull ExecuteListener executeListener) {
        List<String> classNames = getMainFunctionClassName(dexFilePath);
        if (classNames.size() == 0) {
            executeListener.onExecuteError(new Throwable("该Dex文件没有主函数，无法运行：" + dexFilePath));
            return;
        }
        exec(dexFilePath, classNames.get(0), args, executeListener);
    }

    /**
     * 执行dex文件（不传入 main 方法所在类的类名全路径，自动检测到第一个为止）
     *
     * @param dexFile         待执行的dex文件
     * @param args            main方法参数
     * @param executeListener 执行监听器
     */
    public void exec(@NonNull File dexFile, String[] args, @NonNull ExecuteListener executeListener) {
        List<String> classNames = getMainFunctionClassName(dexFile);
        if (classNames.size() == 0) {
            executeListener.onExecuteError(new Throwable("该Dex文件没有主函数，无法运行：" + dexFile.getAbsolutePath()));
            return;
        }
        exec(dexFile, classNames.get(0), args, executeListener);
    }

    /**
     * 执行dex文件
     *
     * @param dexFilePath     待执行的dex文件路径
     * @param className       main方法所在的类全路径（若有包名则包括包名在内）
     * @param args            main方法参数
     * @param executeListener 执行监听器
     */
    public void exec(@NonNull String dexFilePath, @NonNull String className, String[] args, @NonNull ExecuteListener executeListener) {
        exec(new File(dexFilePath), className, args, executeListener);
    }


    /**
     * 执行dex文件
     *
     * @param dexFile         待执行的dex文件
     * @param className       main方法所在的类全路径（若有包名则包括包名在内）
     * @param args            main方法参数
     * @param executeListener 执行监听器
     */
    public void exec(@NonNull final File dexFile, @NonNull final String className, final String[] args, @NonNull final ExecuteListener executeListener) {
        ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Object>() {
            @Override
            public Object doInBackground() throws Throwable {
                String optimizedDirectory = Utils.getApp().getCacheDir().getAbsolutePath() + "/dex";
                FileUtils.createOrExistsDir(optimizedDirectory);
                DexClassLoader dexClassLoader = new DexClassLoader(dexFile.getAbsolutePath(), optimizedDirectory,
                        JavaEngineSetting.getRtPath(), ClassLoader.getSystemClassLoader());
                // 加载 Class
                Class<?> clazz = dexClassLoader.loadClass(className);
                // 获取main方法
                Method method = clazz.getDeclaredMethod("main", String[].class);
                // 调用静态方法可以直接传 null
                method.invoke(null, new Object[]{args});
                return null;
            }

            @Override
            public void onFail(Throwable t) {
                executeListener.onExecuteError(t);
            }

            @Override
            public void onSuccess(Object result) {
                executeListener.onExecuteFinish();
            }
        });
    }

    /**
     * 检测含有 main 方法的类
     *
     * @param dexFilePath Dex文件路径
     * @return 返回存在 main 方法的所有类 的全路径集合 例如 package.Hello
     */
    public List<String> getMainFunctionClassName(String dexFilePath) {
        return getMainFunctionClassName(new File(dexFilePath));
    }

    /**
     * 检测含有 main 方法的类
     *
     * @param dexFile Dex文件
     * @return 返回存在 main 方法的所有类 的全路径集合
     */
    public List<String> getMainFunctionClassName(File dexFile) {
        List<String> classNames = new ArrayList<>();
        try {
            Dex dex = new Dex(dexFile);
            List<MethodId> methodIds = dex.methodIds();
            for (MethodId method : methodIds) {
                // 类路径 例如：Lpackage/Hello;
                String declaringClass = dex.typeNames().get(method.getDeclaringClassIndex());
                // 方法名 例如：main
                String methodName = dex.strings().get(method.getNameIndex());
                // 参数类型 例如：([Ljava/lang/String;)
                String param = dex.readTypeList(dex.protoIds().get(method.getProtoIndex()).getParametersOffset()).toString();
                if (StringUtils.equals(methodName, "main") && StringUtils.equals(param, "([Ljava/lang/String;)")) {
                    String className = declaringClass.replace("/", ".");
                    className = className.replace("L", "");
                    className = className.replace(";", "");
                    classNames.add(className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classNames;
    }
}
