

package com.xiaoyv.dx.cf.iface;

/**
 * Interface for lists of methods.
 */
public interface MethodList {
    /**
     * Get whether this instance is mutable. Note that the
     * {@code MethodList} interface itself doesn't provide any means
     * of mutation, but that doesn't mean that there isn't a non-interface
     * way of mutating an instance.
     *
     * @return {@code true} iff this instance is somehow mutable
     */
    public boolean isMutable();

    /**
     * Get the number of methods in the list.
     *
     * @return the size
     */
    public int size();

    /**
     * Get the {@code n}th method.
     *
     * @param n {@code n >= 0, n < size();} which method
     * @return {@code non-null;} the method in question
     */
    public Method get(int n);
}
