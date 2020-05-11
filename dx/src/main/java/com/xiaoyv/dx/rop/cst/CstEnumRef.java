

package com.xiaoyv.dx.rop.cst;

import com.xiaoyv.dx.rop.type.Type;

/**
 * Constant type to represent a reference to a particular constant
 * value of an enumerated type.
 */
public final class CstEnumRef extends CstMemberRef {
    /**
     * {@code null-ok;} the corresponding field ref, lazily initialized
     */
    private CstFieldRef fieldRef;

    /**
     * Constructs an instance.
     *
     * @param nat {@code non-null;} the name-and-type; the defining class is derived
     *            from this
     */
    public CstEnumRef(CstNat nat) {
        super(new CstType(nat.getFieldType()), nat);

        fieldRef = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String typeName() {
        return "enum";
    }

    /**
     * {@inheritDoc}
     *
     * <b>Note:</b> This returns the enumerated type.
     */
    public Type getType() {
        return getDefiningClass().getClassType();
    }

    /**
     * Get a {@link CstFieldRef} that corresponds with this instance.
     *
     * @return {@code non-null;} the corresponding field reference
     */
    public CstFieldRef getFieldRef() {
        if (fieldRef == null) {
            fieldRef = new CstFieldRef(getDefiningClass(), getNat());
        }

        return fieldRef;
    }
}
