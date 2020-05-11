

package com.xiaoyv.dex.util;

/**
 * A byte sink.
 */
public interface ByteOutput {

    /**
     * Writes a byte.
     *
     * @throws IndexOutOfBoundsException if all bytes have been written.
     */
    void writeByte(int i);
}
