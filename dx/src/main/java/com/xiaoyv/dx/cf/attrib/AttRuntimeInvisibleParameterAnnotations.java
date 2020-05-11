

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.rop.annotation.AnnotationsList;

/**
 * Attribute class for standard
 * {@code RuntimeInvisibleParameterAnnotations} attributes.
 */
public final class AttRuntimeInvisibleParameterAnnotations
        extends BaseParameterAnnotations {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME =
            "RuntimeInvisibleParameterAnnotations";

    /**
     * Constructs an instance.
     *
     * @param parameterAnnotations {@code non-null;} the parameter annotations
     * @param byteLength           {@code >= 0;} attribute data length in the original
     *                             classfile (not including the attribute header)
     */
    public AttRuntimeInvisibleParameterAnnotations(
            AnnotationsList parameterAnnotations, int byteLength) {
        super(ATTRIBUTE_NAME, parameterAnnotations, byteLength);
    }
}
