

package com.xiaoyv.dx.cf.iface;

import com.xiaoyv.dx.rop.cst.TypedConstant;

/**
 * Interface representing fields of class files.
 */
public interface Field
        extends Member {
    /**
     * Get the constant value for this field, if any. This only returns
     * non-{@code null} for a {@code static final} field which
     * includes a {@code ConstantValue} attribute.
     *
     * @return {@code null-ok;} the constant value, or {@code null} if this
     * field isn't a constant
     */
    public TypedConstant getConstantValue();
}
