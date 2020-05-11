

package com.xiaoyv.dx.io.instructions;

import java.io.EOFException;

/**
 * Implementation of {@code CodeInput} that reads from a {@code short[]}.
 */
public final class ShortArrayCodeInput extends BaseCodeCursor
        implements CodeInput {
    /**
     * source array to read from
     */
    private final short[] array;

    /**
     * Constructs an instance.
     */
    public ShortArrayCodeInput(short[] array) {
        if (array == null) {
            throw new NullPointerException("array == null");
        }

        this.array = array;
    }

    /**
     * @inheritDoc
     */
    public boolean hasMore() {
        return cursor() < array.length;
    }

    /**
     * @inheritDoc
     */
    public int read() throws EOFException {
        try {
            int value = array[cursor()];
            advance(1);
            return value & 0xffff;
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new EOFException();
        }
    }

    /**
     * @inheritDoc
     */
    public int readInt() throws EOFException {
        int short0 = read();
        int short1 = read();

        return short0 | (short1 << 16);
    }

    /**
     * @inheritDoc
     */
    public long readLong() throws EOFException {
        long short0 = read();
        long short1 = read();
        long short2 = read();
        long short3 = read();

        return short0 | (short1 << 16) | (short2 << 32) | (short3 << 48);
    }
}
