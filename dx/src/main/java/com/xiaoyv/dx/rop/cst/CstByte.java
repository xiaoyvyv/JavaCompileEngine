

package com.xiaoyv.dx.rop.cst;

import com.xiaoyv.dx.rop.type.Type;
import com.xiaoyv.dx.util.Hex;

/**
 * Constants of type {@code byte}.
 */
public final class CstByte
        extends CstLiteral32 {
    /**
     * {@code non-null;} the value {@code 0} as an instance of this class
     */
    public static final CstByte VALUE_0 = make((byte) 0);

    /**
     * Makes an instance for the given value. This may (but does not
     * necessarily) return an already-allocated instance.
     *
     * @param value the {@code byte} value
     */
    public static CstByte make(byte value) {
        return new CstByte(value);
    }

    /**
     * Makes an instance for the given {@code int} value. This
     * may (but does not necessarily) return an already-allocated
     * instance.
     *
     * @param value the value, which must be in range for a {@code byte}
     * @return {@code non-null;} the appropriate instance
     */
    public static CstByte make(int value) {
        byte cast = (byte) value;

        if (cast != value) {
            throw new IllegalArgumentException("bogus byte value: " +
                    value);
        }

        return make(cast);
    }

    /**
     * Constructs an instance. This constructor is private; use {@link #make}.
     *
     * @param value the {@code byte} value
     */
    private CstByte(byte value) {
        super(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        int value = getIntBits();
        return "byte{0x" + Hex.u1(value) + " / " + value + '}';
    }

    /**
     * {@inheritDoc}
     */
    public Type getType() {
        return Type.BYTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String typeName() {
        return "byte";
    }

    /**
     * {@inheritDoc}
     */
    public String toHuman() {
        return Integer.toString(getIntBits());
    }

    /**
     * Gets the {@code byte} value.
     *
     * @return the value
     */
    public byte getValue() {
        return (byte) getIntBits();
    }
}
