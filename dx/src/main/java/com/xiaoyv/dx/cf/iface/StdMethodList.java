

package com.xiaoyv.dx.cf.iface;

import com.xiaoyv.dx.util.FixedSizeList;

/**
 * Standard implementation of {@link MethodList}, which directly stores
 * an array of {@link Method} objects and can be made immutable.
 */
public final class StdMethodList extends FixedSizeList implements MethodList {
    /**
     * Constructs an instance. All indices initially contain {@code null}.
     *
     * @param size the size of the list
     */
    public StdMethodList(int size) {
        super(size);
    }

    /**
     * {@inheritDoc}
     */
    public Method get(int n) {
        return (Method) get0(n);
    }

    /**
     * Sets the method at the given index.
     *
     * @param n      {@code >= 0, < size();} which method
     * @param method {@code null-ok;} the method object
     */
    public void set(int n, Method method) {
        set0(n, method);
    }
}
