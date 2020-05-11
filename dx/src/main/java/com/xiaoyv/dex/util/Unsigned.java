

package com.xiaoyv.dex.util;

/**
 * Unsigned arithmetic over Java's signed types.
 */
public final class Unsigned {
    private Unsigned() {
    }

    public static int compare(short ushortA, short ushortB) {
        if (ushortA == ushortB) {
            return 0;
        }
        int a = ushortA & 0xFFFF;
        int b = ushortB & 0xFFFF;
        return a < b ? -1 : 1;
    }

    public static int compare(int uintA, int uintB) {
        if (uintA == uintB) {
            return 0;
        }
        long a = uintA & 0xFFFFFFFFL;
        long b = uintB & 0xFFFFFFFFL;
        return a < b ? -1 : 1;
    }
}
