

package com.xiaoyv.dx.io.instructions;

/**
 * Output stream of code units, for writing out Dalvik bytecode.
 */
public interface CodeOutput extends CodeCursor {
    /**
     * Writes a code unit.
     */
    public void write(short codeUnit);

    /**
     * Writes two code units.
     */
    public void write(short u0, short u1);

    /**
     * Writes three code units.
     */
    public void write(short u0, short u1, short u2);

    /**
     * Writes four code units.
     */
    public void write(short u0, short u1, short u2, short u3);

    /**
     * Writes five code units.
     */
    public void write(short u0, short u1, short u2, short u3, short u4);

    /**
     * Writes an {@code int}, little-endian.
     */
    public void writeInt(int value);

    /**
     * Writes a {@code long}, little-endian.
     */
    public void writeLong(long value);

    /**
     * Writes the contents of the given array.
     */
    public void write(byte[] data);

    /**
     * Writes the contents of the given array.
     */
    public void write(short[] data);

    /**
     * Writes the contents of the given array.
     */
    public void write(int[] data);

    /**
     * Writes the contents of the given array.
     */
    public void write(long[] data);
}
