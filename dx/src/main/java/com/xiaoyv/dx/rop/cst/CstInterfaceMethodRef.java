

package com.xiaoyv.dx.rop.cst;

/**
 * Constants of type {@code CONSTANT_InterfaceMethodref_info}.
 */
public final class CstInterfaceMethodRef
        extends CstBaseMethodRef {
    /**
     * {@code null-ok;} normal {@link CstMethodRef} that corresponds to this
     * instance, if calculated
     */
    private CstMethodRef methodRef;

    /**
     * Constructs an instance.
     *
     * @param definingClass {@code non-null;} the type of the defining class
     * @param nat           {@code non-null;} the name-and-type
     */
    public CstInterfaceMethodRef(CstType definingClass, CstNat nat) {
        super(definingClass, nat);
        methodRef = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String typeName() {
        return "ifaceMethod";
    }

    /**
     * Gets a normal (non-interface) {@link CstMethodRef} that corresponds to
     * this instance.
     *
     * @return {@code non-null;} an appropriate instance
     */
    public CstMethodRef toMethodRef() {
        if (methodRef == null) {
            methodRef = new CstMethodRef(getDefiningClass(), getNat());
        }

        return methodRef;
    }
}
