

package com.xiaoyv.dx.cf.attrib;

/**
 * Attribute class for standard {@code Deprecated} attributes.
 */
public final class AttDeprecated extends BaseAttribute {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME = "Deprecated";

    /**
     * Constructs an instance.
     */
    public AttDeprecated() {
        super(ATTRIBUTE_NAME);
    }

    /**
     * {@inheritDoc}
     */
    public int byteLength() {
        return 6;
    }
}
