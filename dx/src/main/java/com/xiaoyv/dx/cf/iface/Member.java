

package com.xiaoyv.dx.cf.iface;

import com.xiaoyv.dx.rop.cst.CstNat;
import com.xiaoyv.dx.rop.cst.CstString;
import com.xiaoyv.dx.rop.cst.CstType;

/**
 * Interface representing members of class files (that is, fields and methods).
 */
public interface Member extends HasAttribute {
    /**
     * Get the defining class.
     *
     * @return {@code non-null;} the defining class
     */
    public CstType getDefiningClass();

    /**
     * Get the field {@code access_flags}.
     *
     * @return the access flags
     */
    public int getAccessFlags();

    /**
     * Get the field {@code name_index} of the member. This is
     * just a convenient shorthand for {@code getNat().getName()}.
     *
     * @return {@code non-null;} the name
     */
    public CstString getName();

    /**
     * Get the field {@code descriptor_index} of the member. This is
     * just a convenient shorthand for {@code getNat().getDescriptor()}.
     *
     * @return {@code non-null;} the descriptor
     */
    public CstString getDescriptor();

    /**
     * Get the name and type associated with this member. This is a
     * combination of the fields {@code name_index} and
     * {@code descriptor_index} in the original classfile, interpreted
     * via the constant pool.
     *
     * @return {@code non-null;} the name and type
     */
    public CstNat getNat();

    /**
     * Get the field {@code attributes} (along with
     * {@code attributes_count}).
     *
     * @return {@code non-null;} the constant pool
     */
    public AttributeList getAttributes();
}
