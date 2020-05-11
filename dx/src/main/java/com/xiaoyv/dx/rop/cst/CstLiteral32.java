

package com.xiaoyv.dx.rop.cst;

/**
 * Constants which are literal 32-bit values of some sort.
 */
public abstract class CstLiteral32
        extends CstLiteralBits {
    /**
     * the value as {@code int} bits
     */
    private final int bits;

    /**
     * Constructs an instance.
     *
     * @param bits the value as {@code int} bits
     */
    /*package*/ CstLiteral32(int bits) {
        this.bits = bits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object other) {
        return (other != null) &&
                (getClass() == other.getClass()) &&
                bits == ((CstLiteral32) other).bits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return bits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int compareTo0(Constant other) {
        int otherBits = ((CstLiteral32) other).bits;

        if (bits < otherBits) {
            return -1;
        } else if (bits > otherBits) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isCategory2() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean fitsInInt() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getIntBits() {
        return bits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long getLongBits() {
        return (long) bits;
    }
}
