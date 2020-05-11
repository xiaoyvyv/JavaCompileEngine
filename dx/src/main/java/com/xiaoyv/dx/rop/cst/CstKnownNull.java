

package com.xiaoyv.dx.rop.cst;

import com.xiaoyv.dx.rop.type.Type;

/**
 * Constant type to represent a known-{@code null} value.
 */
public final class CstKnownNull extends CstLiteralBits {
    /**
     * {@code non-null;} unique instance of this class
     */
    public static final CstKnownNull THE_ONE = new CstKnownNull();

    /**
     * Constructs an instance. This class is not publicly instantiable. Use
     * {@link #THE_ONE}.
     */
    private CstKnownNull() {
        // This space intentionally left blank.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return (other instanceof CstKnownNull);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return 0x4466757a;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int compareTo0(Constant other) {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "known-null";
    }

    /**
     * {@inheritDoc}
     */
    public Type getType() {
        return Type.KNOWN_NULL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String typeName() {
        return "known-null";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCategory2() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public String toHuman() {
        return "null";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean fitsInInt() {
        // See comment in getIntBits().
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * As "literal bits," a known-null is always represented as the
     * number zero.
     */
    @Override
    public int getIntBits() {
        return 0;
    }

    /**
     * {@inheritDoc}
     * <p>
     * As "literal bits," a known-null is always represented as the
     * number zero.
     */
    @Override
    public long getLongBits() {
        return 0;
    }
}
