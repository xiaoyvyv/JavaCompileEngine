package com.xiaoyv.javaengine.executor.helper;


import androidx.annotation.NonNull;

import com.xiaoyv.javaengine.utils.FileIOUtils;
import com.xiaoyv.javaengine.utils.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by 王怀玉 on 18/07/2017.
 */

public class ClassHelper {
    private static final Pattern MAIN_FUNCTION;

    static {
        MAIN_FUNCTION = Pattern.compile("(public\\s+static\\s+void\\s+main\\s?)" + // public static void main
                        "(\\(\\s?String\\s?\\[\\s?\\]\\s?\\w+\\s?\\)|" + // String[] args
                        "(\\(\\s?String\\s+\\w+\\[\\s?\\]\\s?\\)))", // String args[]
                Pattern.DOTALL);
    }

    /**
     * 检测 代码是否含有 public static void main(String[] args) {}方法
     */
    private static boolean matchMainFunction(String content) {
        return MAIN_FUNCTION.matcher(content).find();
    }

    /**
     * 检测java文件是否有主方法
     */
    public static boolean hasMainFunction(File javaFile) {
        String s = FileIOUtils.readFile2String(javaFile);
        return matchMainFunction(s);
    }

    /**
     * 检测java文件是否有主方法
     */
    public static boolean hasMainFunction(String javaFilePath) {
        String s = FileIOUtils.readFile2String(javaFilePath);
        return matchMainFunction(s);
    }


    /**
     * 获取当前项目lib目录的jar文件的 CLASSPATH
     *
     * @param libFolder 当前项目添加的jar依赖文件夹
     * @return 所有依赖转成的 -classpath 格式字符串
     */
    public static String getLibClassPath(@NonNull File libFolder) {
        boolean existsDir = FileUtils.createOrExistsDir(libFolder);

        if (!existsDir) {
            return "";
        }

        List<File> javaLibraries = FileUtils.listFilesInDirWithFilter(libFolder, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return FileUtils.isFile(pathname) && FileUtils.getFileName(pathname).endsWith(".jar");
            }
        });
        if (javaLibraries == null) {
            return "";
        }

        StringBuilder classpath = new StringBuilder(".");
        for (File javaLibrary : javaLibraries) {
            if (classpath.length() != 0) {
                classpath.append(File.pathSeparator);
            }
            classpath.append(javaLibrary.getAbsolutePath());
        }
        return classpath.toString();
    }

}
