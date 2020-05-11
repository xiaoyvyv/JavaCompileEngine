

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.rop.type.TypeList;
import com.xiaoyv.dx.util.MutabilityException;

/**
 * Attribute class for standard {@code Exceptions} attributes.
 */
public final class AttExceptions extends BaseAttribute {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME = "Exceptions";

    /**
     * {@code non-null;} list of exception classes
     */
    private final TypeList exceptions;

    /**
     * Constructs an instance.
     *
     * @param exceptions {@code non-null;} list of classes, presumed but not
     *                   verified to be subclasses of {@code Throwable}
     */
    public AttExceptions(TypeList exceptions) {
        super(ATTRIBUTE_NAME);

        try {
            if (exceptions.isMutable()) {
                throw new MutabilityException("exceptions.isMutable()");
            }
        } catch (NullPointerException ex) {
            // Translate the exception.
            throw new NullPointerException("exceptions == null");
        }

        this.exceptions = exceptions;
    }

    /**
     * {@inheritDoc}
     */
    public int byteLength() {
        return 8 + exceptions.size() * 2;
    }

    /**
     * Gets the list of classes associated with this instance. In
     * general, these classes are not pre-verified to be subclasses of
     * {@code Throwable}.
     *
     * @return {@code non-null;} the list of classes
     */
    public TypeList getExceptions() {
        return exceptions;
    }
}
