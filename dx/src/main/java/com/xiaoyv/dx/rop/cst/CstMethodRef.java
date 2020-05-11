

package com.xiaoyv.dx.rop.cst;

/**
 * Constants of type {@code CONSTANT_Methodref_info}.
 */
public final class CstMethodRef
        extends CstBaseMethodRef {
    /**
     * Constructs an instance.
     *
     * @param definingClass {@code non-null;} the type of the defining class
     * @param nat           {@code non-null;} the name-and-type
     */
    public CstMethodRef(CstType definingClass, CstNat nat) {
        super(definingClass, nat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String typeName() {
        return "method";
    }
}
