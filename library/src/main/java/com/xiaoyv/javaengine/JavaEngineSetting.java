package com.xiaoyv.javaengine;

import com.xiaoyv.javaengine.utils.SPUtils;
import com.xiaoyv.javaengine.utils.StringUtils;
import com.xiaoyv.javaengine.utils.Utils;

public class JavaEngineSetting {
    public static final String KEY = "JavaSetting";
    private static final String sourceVersion = "1.8";
    private static final String targetVersion = "1.8";
    private static final String compileEncoding = "UTF-8";

    public static String getRtPath() {
        String rtFilePath = Utils.getApp().getFilesDir() + "/lib/rt.jar";
        String path = SPUtils.getInstance(KEY).getString("rt_path");
        return StringUtils.isEmpty(path) ? rtFilePath : path;
    }

    public static void restore() {
        String rtFilePath = Utils.getApp().getFilesDir() + "/lib/rt.jar";
        SPUtils.getInstance(KEY).put("rt_path", rtFilePath);
        SPUtils.getInstance(KEY).put("formatter", "2");
        SPUtils.getInstance(KEY).put("compile_source", sourceVersion);
        SPUtils.getInstance(KEY).put("compile_target", targetVersion);
        SPUtils.getInstance(KEY).put("compile_encoding", compileEncoding);
        SPUtils.getInstance(KEY).put("compile_verbose", false);
        SPUtils.getInstance(KEY).put("editor_row", true);
        SPUtils.getInstance(KEY).put("editor_auto_compete", true);
        SPUtils.getInstance(KEY).put("editor_dark_mode", false);
        SPUtils.getInstance(KEY).put("editor_auto_save", true);
        SPUtils.getInstance(KEY).put("run_args", "args");
    }

    public static String getClassSourceVersion() {
        String version = SPUtils.getInstance(KEY).getString("compile_source");
        return StringUtils.isEmpty(version) ? sourceVersion : version;
    }

    public static String getClassTargetVersion() {
        String version = SPUtils.getInstance(KEY).getString("compile_target");
        return StringUtils.isEmpty(version) ? targetVersion : version;
    }

    public static String getCompileEncoding() {
        String encoding = SPUtils.getInstance(KEY).getString("compile_encoding");
        return StringUtils.isEmpty(encoding) ? compileEncoding : encoding;
    }

    public static boolean isClassVerbose() {
        return SPUtils.getInstance(KEY).getBoolean("compile_verbose", false);
    }



    public static String getMainArgs() {
        return SPUtils.getInstance(KEY).getString("run_args", "args");
    }


    public static boolean isAutoRunWhenCompiled() {
        return SPUtils.getInstance(KEY).getBoolean("compile_auto_run", true);
    }

    public static boolean isShowRow() {
        return SPUtils.getInstance(KEY).getBoolean("editor_row", true);
    }


    public static boolean isAutoCompete() {
        return SPUtils.getInstance(KEY).getBoolean("editor_auto_compete", true);
    }

    public static boolean isDarkMode() {
        return SPUtils.getInstance(KEY).getBoolean("editor_dark_mode", false);
    }

    public static boolean isAutoSaveWhenExit() {
        return SPUtils.getInstance(KEY).getBoolean("editor_auto_save", true);
    }

}
