

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.rop.annotation.AnnotationsList;
import com.xiaoyv.dx.util.MutabilityException;

/**
 * Base class for parameter annotation list attributes.
 */
public abstract class BaseParameterAnnotations extends BaseAttribute {
    /**
     * {@code non-null;} list of annotations
     */
    private final AnnotationsList parameterAnnotations;

    /**
     * {@code >= 0;} attribute data length in the original classfile (not
     * including the attribute header)
     */
    private final int byteLength;

    /**
     * Constructs an instance.
     *
     * @param attributeName        {@code non-null;} the name of the attribute
     * @param parameterAnnotations {@code non-null;} the annotations
     * @param byteLength           {@code >= 0;} attribute data length in the original
     *                             classfile (not including the attribute header)
     */
    public BaseParameterAnnotations(String attributeName,
                                    AnnotationsList parameterAnnotations, int byteLength) {
        super(attributeName);

        try {
            if (parameterAnnotations.isMutable()) {
                throw new MutabilityException(
                        "parameterAnnotations.isMutable()");
            }
        } catch (NullPointerException ex) {
            // Translate the exception.
            throw new NullPointerException("parameterAnnotations == null");
        }

        this.parameterAnnotations = parameterAnnotations;
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
     * Gets the list of annotation lists associated with this instance.
     *
     * @return {@code non-null;} the list
     */
    public final AnnotationsList getParameterAnnotations() {
        return parameterAnnotations;
    }
}
