

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.rop.annotation.Annotations;

/**
 * Attribute class for standard {@code RuntimeInvisibleAnnotations}
 * attributes.
 */
public final class AttRuntimeInvisibleAnnotations extends BaseAnnotations {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME = "RuntimeInvisibleAnnotations";

    /**
     * Constructs an instance.
     *
     * @param annotations {@code non-null;} the list of annotations
     * @param byteLength  {@code >= 0;} attribute data length in the original
     *                    classfile (not including the attribute header)
     */
    public AttRuntimeInvisibleAnnotations(Annotations annotations,
                                          int byteLength) {
        super(ATTRIBUTE_NAME, annotations, byteLength);
    }
}
