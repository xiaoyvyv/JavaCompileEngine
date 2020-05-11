

package com.xiaoyv.dx.cf.iface;

import com.xiaoyv.dx.util.FixedSizeList;

/**
 * Standard implementation of {@link FieldList}, which directly stores
 * an array of {@link Field} objects and can be made immutable.
 */
public final class StdFieldList extends FixedSizeList implements FieldList {
    /**
     * Constructs an instance. All indices initially contain {@code null}.
     *
     * @param size the size of the list
     */
    public StdFieldList(int size) {
        super(size);
    }

    /**
     * {@inheritDoc}
     */
    public Field get(int n) {
        return (Field) get0(n);
    }

    /**
     * Sets the field at the given index.
     *
     * @param n     {@code >= 0, < size();} which field
     * @param field {@code null-ok;} the field object
     */
    public void set(int n, Field field) {
        set0(n, field);
    }
}
