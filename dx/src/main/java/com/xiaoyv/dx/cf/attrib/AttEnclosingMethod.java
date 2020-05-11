

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.rop.cst.CstNat;
import com.xiaoyv.dx.rop.cst.CstType;

/**
 * Attribute class for standards-track {@code EnclosingMethod}
 * attributes.
 */
public final class AttEnclosingMethod extends BaseAttribute {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME = "EnclosingMethod";

    /**
     * {@code non-null;} the innermost enclosing class
     */
    private final CstType type;

    /**
     * {@code null-ok;} the name-and-type of the innermost enclosing method, if any
     */
    private final CstNat method;

    /**
     * Constructs an instance.
     *
     * @param type   {@code non-null;} the innermost enclosing class
     * @param method {@code null-ok;} the name-and-type of the innermost enclosing
     *               method, if any
     */
    public AttEnclosingMethod(CstType type, CstNat method) {
        super(ATTRIBUTE_NAME);

        if (type == null) {
            throw new NullPointerException("type == null");
        }

        this.type = type;
        this.method = method;
    }

    /**
     * {@inheritDoc}
     */
    public int byteLength() {
        return 10;
    }

    /**
     * Gets the innermost enclosing class.
     *
     * @return {@code non-null;} the innermost enclosing class
     */
    public CstType getEnclosingClass() {
        return type;
    }

    /**
     * Gets the name-and-type of the innermost enclosing method, if
     * any.
     *
     * @return {@code null-ok;} the name-and-type of the innermost enclosing
     * method, if any
     */
    public CstNat getMethod() {
        return method;
    }
}
