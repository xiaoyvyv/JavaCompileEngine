

package com.xiaoyv.dx.rop.cst;

import com.xiaoyv.dx.rop.type.Type;
import com.xiaoyv.dx.util.Hex;

/**
 * Constants of type {@code CONSTANT_Double_info}.
 */
public final class CstDouble
        extends CstLiteral64 {
    /**
     * {@code non-null;} instance representing {@code 0}
     */
    public static final CstDouble VALUE_0 =
            new CstDouble(Double.doubleToLongBits(0.0));

    /**
     * {@code non-null;} instance representing {@code 1}
     */
    public static final CstDouble VALUE_1 =
            new CstDouble(Double.doubleToLongBits(1.0));

    /**
     * Makes an instance for the given value. This may (but does not
     * necessarily) return an already-allocated instance.
     *
     * @param bits the {@code double} value as {@code long} bits
     */
    public static CstDouble make(long bits) {
        /*
         * Note: Javadoc notwithstanding, this implementation always
         * allocates.
         */
        return new CstDouble(bits);
    }

    /**
     * Constructs an instance. This constructor is private; use {@link #make}.
     *
     * @param bits the {@code double} value as {@code long} bits
     */
    private CstDouble(long bits) {
        super(bits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        long bits = getLongBits();
        return "double{0x" + Hex.u8(bits) + " / " +
                Double.longBitsToDouble(bits) + '}';
    }

    /**
     * {@inheritDoc}
     */
    public Type getType() {
        return Type.DOUBLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String typeName() {
        return "double";
    }

    /**
     * {@inheritDoc}
     */
    public String toHuman() {
        return Double.toString(Double.longBitsToDouble(getLongBits()));
    }

    /**
     * Gets the {@code double} value.
     *
     * @return the value
     */
    public double getValue() {
        return Double.longBitsToDouble(getLongBits());
    }
}
