

package com.xiaoyv.dx.cf.attrib;

/**
 * Attribute class for standard {@code Synthetic} attributes.
 */
public final class AttSynthetic extends BaseAttribute {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME = "Synthetic";

    /**
     * Constructs an instance.
     */
    public AttSynthetic() {
        super(ATTRIBUTE_NAME);
    }

    /**
     * {@inheritDoc}
     */
    public int byteLength() {
        return 6;
    }
}
