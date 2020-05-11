

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.rop.annotation.Annotations;
import com.xiaoyv.dx.util.MutabilityException;

/**
 * Base class for annotations attributes.
 */
public abstract class BaseAnnotations extends BaseAttribute {
    /**
     * {@code non-null;} list of annotations
     */
    private final Annotations annotations;

    /**
     * {@code >= 0;} attribute data length in the original classfile (not
     * including the attribute header)
     */
    private final int byteLength;

    /**
     * Constructs an instance.
     *
     * @param attributeName {@code non-null;} the name of the attribute
     * @param annotations   {@code non-null;} the list of annotations
     * @param byteLength    {@code >= 0;} attribute data length in the original
     *                      classfile (not including the attribute header)
     */
    public BaseAnnotations(String attributeName, Annotations annotations,
                           int byteLength) {
        super(attributeName);

        try {
            if (annotations.isMutable()) {
                throw new MutabilityException("annotations.isMutable()");
            }
        } catch (NullPointerException ex) {
            // Translate the exception.
            throw new NullPointerException("annotations == null");
        }

        this.annotations = annotations;
        this.byteLength = byteLength;
    }

    /**
     * {@inheritDoc}
     */
    public final int byteLength() {
        // Add six for the standard attribute header.
        return byteLength + 6;
    }

    /**
     * Gets the list of annotations associated with this instance.
     *
     * @return {@code non-null;} the list
     */
    public final Annotations getAnnotations() {
        return annotations;
    }
}
