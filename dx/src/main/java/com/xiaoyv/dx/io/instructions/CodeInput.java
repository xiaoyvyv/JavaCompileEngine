

package com.xiaoyv.dx.io.instructions;

import java.io.EOFException;

/**
 * Input stream of code units, for reading in Dalvik bytecode.
 */
public interface CodeInput extends CodeCursor {
    /**
     * Returns whether there are any more code units to read. This
     * is analogous to {@code hasNext()} on an interator.
     */
    public boolean hasMore();

    /**
     * Reads a code unit.
     */
    public int read() throws EOFException;

    /**
     * Reads two code units, treating them as a little-endian {@code int}.
     */
    public int readInt() throws EOFException;

    /**
     * Reads four code units, treating them as a little-endian {@code long}.
     */
    public long readLong() throws EOFException;
}
