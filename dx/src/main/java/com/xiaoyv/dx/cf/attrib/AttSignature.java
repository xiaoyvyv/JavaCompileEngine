

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.rop.cst.CstString;

/**
 * Attribute class for standards-track {@code Signature} attributes.
 */
public final class AttSignature extends BaseAttribute {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME = "Signature";

    /**
     * {@code non-null;} the signature string
     */
    private final CstString signature;

    /**
     * Constructs an instance.
     *
     * @param signature {@code non-null;} the signature string
     */
    public AttSignature(CstString signature) {
        super(ATTRIBUTE_NAME);

        if (signature == null) {
            throw new NullPointerException("signature == null");
        }

        this.signature = signature;
    }

    /**
     * {@inheritDoc}
     */
    public int byteLength() {
        return 8;
    }

    /**
     * Gets the signature string.
     *
     * @return {@code non-null;} the signature string
     */
    public CstString getSignature() {
        return signature;
    }
}
