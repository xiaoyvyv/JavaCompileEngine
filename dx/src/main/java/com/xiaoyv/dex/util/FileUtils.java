

package com.xiaoyv.dex.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * File I/O utilities.
 */
public final class FileUtils {
    private FileUtils() {
    }

    /**
     * Reads the named file, translating {@link IOException} to a
     * {@link RuntimeException} of some sort.
     *
     * @param fileName {@code non-null;} name of the file to read
     * @return {@code non-null;} contents of the file
     */
    public static byte[] readFile(String fileName) {
        File file = new File(fileName);
        return readFile(file);
    }

    /**
     * Reads the given file, translating {@link IOException} to a
     * {@link RuntimeException} of some sort.
     *
     * @param file {@code non-null;} the file to read
     * @return {@code non-null;} contents of the file
     */
    public static byte[] readFile(File file) {
        if (!file.exists()) {
            throw new RuntimeException(file + ": file not found");
        }

        if (!file.isFile()) {
            throw new RuntimeException(file + ": not a file");
        }

        if (!file.canRead()) {
            throw new RuntimeException(file + ": file not readable");
        }

        long longLength = file.length();
        int length = (int) longLength;
        if (length != longLength) {
            throw new RuntimeException(file + ": file too long");
        }

        byte[] result = new byte[length];

        try {
            FileInputStream in = new FileInputStream(file);
            int at = 0;
            while (length > 0) {
                int amt = in.read(result, at, length);
                if (amt == -1) {
                    throw new RuntimeException(file + ": unexpected EOF");
                }
                at += amt;
                length -= amt;
            }
            in.close();
        } catch (IOException ex) {
            throw new RuntimeException(file + ": trouble reading", ex);
        }

        return result;
    }

    /**
     * Returns true if {@code fileName} names a .zip, .jar, or .apk.
     */
    public static boolean hasArchiveSuffix(String fileName) {
        return fileName.endsWith(".zip")
                || fileName.endsWith(".jar")
                || fileName.endsWith(".apk");
    }
}
