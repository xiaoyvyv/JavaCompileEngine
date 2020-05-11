

package com.xiaoyv.dx.rop.cst;

import com.xiaoyv.dx.rop.type.Type;
import com.xiaoyv.dx.util.Hex;

/**
 * Constants of type {@code CONSTANT_Long_info}.
 */
public final class CstLong
        extends CstLiteral64 {
    /**
     * {@code non-null;} instance representing {@code 0}
     */
    public static final CstLong VALUE_0 = make(0);

    /**
     * {@code non-null;} instance representing {@code 1}
     */
    public static final CstLong VALUE_1 = make(1);

    /**
     * Makes an instance for the given value. This may (but does not
     * necessarily) return an already-allocated instance.
     *
     * @param value the {@code long} value
     */
    public static CstLong make(long value) {
        /*
         * Note: Javadoc notwithstanding, this implementation always
         * allocates.
         */
        return new CstLong(value);
    }

    /**
     * Constructs an instance. This constructor is private; use {@link #make}.
     *
     * @param value the {@code long} value
     */
    private CstLong(long value) {
        super(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        long value = getLongBits();
        return "long{0x" + Hex.u8(value) + " / " + value + '}';
    }

    /**
     * {@inheritDoc}
     */
    public Type getType() {
        return Type.LONG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String typeName() {
        return "long";
    }

    /**
     * {@inheritDoc}
     */
    public String toHuman() {
        return Long.toString(getLongBits());
    }

    /**
     * Gets the {@code long} value.
     *
     * @return the value
     */
    public long getValue() {
        return getLongBits();
    }
}
