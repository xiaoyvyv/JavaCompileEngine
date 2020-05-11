

package com.xiaoyv.dx.rop.cst;

import com.xiaoyv.dx.rop.type.Type;

/**
 * Utility for turning types into zeroes.
 */
public final class Zeroes {
    /**
     * This class is uninstantiable.
     */
    private Zeroes() {
        // This space intentionally left blank.
    }

    /**
     * Gets the "zero" (or {@code null}) value for the given type.
     *
     * @param type {@code non-null;} the type in question
     * @return {@code non-null;} its "zero" value
     */
    public static Constant zeroFor(Type type) {
        switch (type.getBasicType()) {
            case Type.BT_BOOLEAN:
                return CstBoolean.VALUE_FALSE;
            case Type.BT_BYTE:
                return CstByte.VALUE_0;
            case Type.BT_CHAR:
                return CstChar.VALUE_0;
            case Type.BT_DOUBLE:
                return CstDouble.VALUE_0;
            case Type.BT_FLOAT:
                return CstFloat.VALUE_0;
            case Type.BT_INT:
                return CstInteger.VALUE_0;
            case Type.BT_LONG:
                return CstLong.VALUE_0;
            case Type.BT_SHORT:
                return CstShort.VALUE_0;
            case Type.BT_OBJECT:
                return CstKnownNull.THE_ONE;
            default: {
                throw new UnsupportedOperationException("no zero for type: " +
                        type.toHuman());
            }
        }
    }
}
