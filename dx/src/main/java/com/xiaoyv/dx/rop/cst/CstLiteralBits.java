

package com.xiaoyv.dx.rop.cst;

/**
 * Constants which are literal bitwise values of some sort.
 */
public abstract class CstLiteralBits
        extends TypedConstant {
    /**
     * Returns whether or not this instance's value may be accurately
     * represented as an {@code int}. The rule is that if there
     * is an {@code int} which may be sign-extended to yield this
     * instance's value, then this method returns {@code true}.
     * Otherwise, it returns {@code false}.
     *
     * @return {@code true} iff this instance fits in an {@code int}
     */
    public abstract boolean fitsInInt();

    /**
     * Gets the value as {@code int} bits. If this instance contains
     * more bits than fit in an {@code int}, then this returns only
     * the low-order bits.
     *
     * @return the bits
     */
    public abstract int getIntBits();

    /**
     * Gets the value as {@code long} bits. If this instance contains
     * fewer bits than fit in a {@code long}, then the result of this
     * method is the sign extension of the value.
     *
     * @return the bits
     */
    public abstract long getLongBits();

    /**
     * Returns true if this value can fit in 16 bits with sign-extension.
     *
     * @return true if the sign-extended lower 16 bits are the same as
     * the value.
     */
    public boolean fitsIn16Bits() {
        if (!fitsInInt()) {
            return false;
        }

        int bits = getIntBits();
        return (short) bits == bits;
    }

    /**
     * Returns true if this value can fit in 8 bits with sign-extension.
     *
     * @return true if the sign-extended lower 8 bits are the same as
     * the value.
     */
    public boolean fitsIn8Bits() {
        if (!fitsInInt()) {
            return false;
        }

        int bits = getIntBits();
        return (byte) bits == bits;
    }
}
