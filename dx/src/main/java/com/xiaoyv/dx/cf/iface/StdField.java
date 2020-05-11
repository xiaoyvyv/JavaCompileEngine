

package com.xiaoyv.dx.cf.iface;

import com.xiaoyv.dx.cf.attrib.AttConstantValue;
import com.xiaoyv.dx.rop.cst.CstNat;
import com.xiaoyv.dx.rop.cst.CstType;
import com.xiaoyv.dx.rop.cst.TypedConstant;

/**
 * Standard implementation of {@link Field}, which directly stores
 * all the associated data.
 */
public final class StdField extends StdMember implements Field {
    /**
     * Constructs an instance.
     *
     * @param definingClass {@code non-null;} the defining class
     * @param accessFlags   access flags
     * @param nat           {@code non-null;} member name and type (descriptor)
     * @param attributes    {@code non-null;} list of associated attributes
     */
    public StdField(CstType definingClass, int accessFlags, CstNat nat,
                    AttributeList attributes) {
        super(definingClass, accessFlags, nat, attributes);
    }

    /**
     * {@inheritDoc}
     */
    public TypedConstant getConstantValue() {
        AttributeList attribs = getAttributes();
        AttConstantValue cval = (AttConstantValue)
                attribs.findFirst(AttConstantValue.ATTRIBUTE_NAME);

        if (cval == null) {
            return null;
        }

        return cval.getConstantValue();
    }
}
