package com.xiaoyv.java.compiler.tools.java;

import androidx.annotation.NonNull;

import com.xiaoyv.java.compiler.utils.FileIOUtils;
import com.xiaoyv.java.compiler.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author 王怀玉
 * @date 18/07/2017
 */

public class JavaClassHelper {
    private static final Pattern MAIN_FUNCTION;

    private static final String MAIN_FUN_REGEX =
            "(public\\s+static\\s+void\\s+main\\s?)" +
                    "(\\(\\s?String\\s?\\[\\s?]\\s?\\w+\\s?\\)|" +
                    "(\\(\\s?String\\s+\\w+\\[\\s?]\\s?\\)))";

    static {
        MAIN_FUNCTION = Pattern.compile(MAIN_FUN_REGEX, Pattern.DOTALL);
    }

    /**
     * 检测 代码是否含有 public static void main(String[] args) {}方法
     */
    private static boolean matchMainFunction(String content) {
        return MAIN_FUNCTION.matcher(content).find();
    }

    /**
     * 检测 java 文件是否有主方法
     */
    public static boolean hasMainFunction(File javaFile) {
        String s = FileIOUtils.readFile2String(javaFile);
        return matchMainFunction(s);
    }

    /**
     * 检测 java 文件是否有主方法
     */
    public static boolean hasMainFunction(String javaFilePath) {
        String s = FileIOUtils.readFile2String(javaFilePath);
        return matchMainFunction(s);
    }


    /**
     * 获取当前项目 lib 目录的 jar 文件的 CLASSPATH
     *
     * @param libFolder 当前项目添加的jar依赖文件夹
     * @return 所有依赖转成的 -classpath 格式字符串
     */
    public static String getLibClassPath(@NonNull File libFolder) {
        boolean existsDir = FileUtils.createOrExistsDir(libFolder);

        if (!existsDir) {
            return "";
        }

        List<File> javaLibraries = FileUtils.listFilesInDirWithFilter(libFolder, pathname ->
                FileUtils.isFile(pathname) && FileUtils.getFileName(pathname).endsWith(".jar"));

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
