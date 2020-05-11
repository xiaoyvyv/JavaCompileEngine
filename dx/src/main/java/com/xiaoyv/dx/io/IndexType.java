

package com.xiaoyv.dx.io;

/**
 * The various types that an index in a Dalvik instruction might refer to.
 */
public enum IndexType {
    /**
     * "Unknown." Used for undefined opcodes.
     */
    UNKNOWN,

    /**
     * no index used
     */
    NONE,

    /**
     * "It depends." Used for {@code throw-verification-error}.
     */
    VARIES,

    /**
     * type reference index
     */
    TYPE_REF,

    /**
     * string reference index
     */
    STRING_REF,

    /**
     * method reference index
     */
    METHOD_REF,

    /**
     * field reference index
     */
    FIELD_REF,

    /**
     * inline method index (for inline linked method invocations)
     */
    INLINE_METHOD,

    /**
     * direct vtable offset (for static linked method invocations)
     */
    VTABLE_OFFSET,

    /**
     * direct field offset (for static linked field accesses)
     */
    FIELD_OFFSET;
}
