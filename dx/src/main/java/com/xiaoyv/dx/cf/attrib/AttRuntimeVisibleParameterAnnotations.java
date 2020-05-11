

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.rop.annotation.AnnotationsList;

/**
 * Attribute class for standard {@code RuntimeVisibleParameterAnnotations}
 * attributes.
 */
public final class AttRuntimeVisibleParameterAnnotations
        extends BaseParameterAnnotations {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME =
            "RuntimeVisibleParameterAnnotations";

    /**
     * Constructs an instance.
     *
     * @param annotations {@code non-null;} the parameter annotations
     * @param byteLength  {@code >= 0;} attribute data length in the original
     *                    classfile (not including the attribute header)
     */
    public AttRuntimeVisibleParameterAnnotations(
            AnnotationsList annotations, int byteLength) {
        super(ATTRIBUTE_NAME, annotations, byteLength);
    }
}
