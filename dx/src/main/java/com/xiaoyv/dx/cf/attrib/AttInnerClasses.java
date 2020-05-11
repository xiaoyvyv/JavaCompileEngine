

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.util.MutabilityException;

/**
 * Attribute class for standard {@code InnerClasses} attributes.
 */
public final class AttInnerClasses extends BaseAttribute {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME = "InnerClasses";

    /**
     * {@code non-null;} list of inner class entries
     */
    private final InnerClassList innerClasses;

    /**
     * Constructs an instance.
     *
     * @param innerClasses {@code non-null;} list of inner class entries
     */
    public AttInnerClasses(InnerClassList innerClasses) {
        super(ATTRIBUTE_NAME);

        try {
            if (innerClasses.isMutable()) {
                throw new MutabilityException("innerClasses.isMutable()");
            }
        } catch (NullPointerException ex) {
            // Translate the exception.
            throw new NullPointerException("innerClasses == null");
        }

        this.innerClasses = innerClasses;
    }

    /**
     * {@inheritDoc}
     */
    public int byteLength() {
        return 8 + innerClasses.size() * 8;
    }

    /**
     * Gets the list of "inner class" entries associated with this instance.
     *
     * @return {@code non-null;} the list
     */
    public InnerClassList getInnerClasses() {
        return innerClasses;
    }
}
