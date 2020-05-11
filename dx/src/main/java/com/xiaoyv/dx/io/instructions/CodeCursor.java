

package com.xiaoyv.dx.io.instructions;

/**
 * Cursor over code units, for reading or writing out Dalvik bytecode.
 */
public interface CodeCursor {
    /**
     * Gets the cursor. The cursor is the offset in code units from
     * the start of the input of the next code unit to be read or
     * written, where the input generally consists of the code for a
     * single method.
     */
    public int cursor();

    /**
     * Gets the base address associated with the current cursor. This
     * differs from the cursor value when explicitly set (by {@link
     * #setBaseAddress). This is used, in particular, to convey base
     * addresses to switch data payload instructions, whose relative
     * addresses are relative to the address of a dependant switch
     * instruction.
     */
    public int baseAddressForCursor();

    /**
     * Sets the base address for the given target address to be as indicated.
     *
     * @see #baseAddressForCursor
     */
    public void setBaseAddress(int targetAddress, int baseAddress);
}
