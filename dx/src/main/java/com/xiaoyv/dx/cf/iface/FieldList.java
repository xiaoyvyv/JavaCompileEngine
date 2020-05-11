

package com.xiaoyv.dx.cf.iface;

/**
 * Interface for lists of fields.
 */
public interface FieldList {
    /**
     * Get whether this instance is mutable. Note that the
     * {@code FieldList} interface itself doesn't provide any means
     * of mutation, but that doesn't mean that there isn't a non-interface
     * way of mutating an instance.
     *
     * @return {@code true} iff this instance is somehow mutable
     */
    public boolean isMutable();

    /**
     * Get the number of fields in the list.
     *
     * @return the size
     */
    public int size();

    /**
     * Get the {@code n}th field.
     *
     * @param n {@code n >= 0, n < size();} which field
     * @return {@code non-null;} the field in question
     */
    public Field get(int n);
}
