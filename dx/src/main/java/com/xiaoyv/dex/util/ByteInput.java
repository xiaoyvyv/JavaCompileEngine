

package com.xiaoyv.dex.util;

/**
 * A byte source.
 */
public interface ByteInput {

    /**
     * Returns a byte.
     *
     * @throws IndexOutOfBoundsException if all bytes have been read.
     */
    byte readByte();
}
