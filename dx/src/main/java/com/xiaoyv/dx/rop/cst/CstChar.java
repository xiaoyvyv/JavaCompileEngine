

package com.xiaoyv.dx.rop.cst;

import com.xiaoyv.dx.rop.type.Type;
import com.xiaoyv.dx.util.Hex;

/**
 * Constants of type {@code char}.
 */
public final class CstChar
        extends CstLiteral32 {
    /**
     * {@code non-null;} the value {@code 0} as an instance of this class
     */
    public static final CstChar VALUE_0 = make((char) 0);

    /**
     * Makes an instance for the given value. This may (but does not
     * necessarily) return an already-allocated instance.
     *
     * @param value the {@code char} value
     */
    public static CstChar make(char value) {
        return new CstChar(value);
    }

    /**
     * Makes an instance for the given {@code int} value. This
     * may (but does not necessarily) return an already-allocated
     * instance.
     *
     * @param value the value, which must be in range for a {@code char}
     * @return {@code non-null;} the appropriate instance
     */
    public static CstChar make(int value) {
        char cast = (char) value;

        if (cast != value) {
            throw new IllegalArgumentException("bogus char value: " +
                    value);
        }

        return make(cast);
    }

    /**
     * Constructs an instance. This constructor is private; use {@link #make}.
     *
     * @param value the {@code char} value
     */
    private CstChar(char value) {
        super(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        int value = getIntBits();
        return "char{0x" + Hex.u2(value) + " / " + value + '}';
    }

    /**
     * {@inheritDoc}
     */
    public Type getType() {
        return Type.CHAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String typeName() {
        return "char";
    }

    /**
     * {@inheritDoc}
     */
    public String toHuman() {
        return Integer.toString(getIntBits());
    }

    /**
     * Gets the {@code char} value.
     *
     * @return the value
     */
    public char getValue() {
        return (char) getIntBits();
    }
}
