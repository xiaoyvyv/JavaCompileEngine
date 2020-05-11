

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.rop.cst.Constant;

/**
 * Attribute class for {@code AnnotationDefault} attributes.
 */
public final class AttAnnotationDefault extends com.xiaoyv.dx.cf.attrib.BaseAttribute {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME = "AnnotationDefault";

    /**
     * {@code non-null;} the annotation default value
     */
    private final Constant value;

    /**
     * {@code >= 0;} attribute data length in the original classfile (not
     * including the attribute header)
     */
    private final int byteLength;

    /**
     * Constructs an instance.
     *
     * @param value      {@code non-null;} the annotation default value
     * @param byteLength {@code >= 0;} attribute data length in the original
     *                   classfile (not including the attribute header)
     */
    public AttAnnotationDefault(Constant value, int byteLength) {
        super(ATTRIBUTE_NAME);

        if (value == null) {
            throw new NullPointerException("value == null");
        }

        this.value = value;
        this.byteLength = byteLength;
    }

    /**
     * {@inheritDoc}
     */
    public int byteLength() {
        // Add six for the standard attribute header.
        return byteLength + 6;
    }

    /**
     * Gets the annotation default value.
     *
     * @return {@code non-null;} the value
     */
    public Constant getValue() {
        return value;
    }
}
