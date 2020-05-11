

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.cf.iface.Attribute;

/**
 * Base implementation of {@link Attribute}, which directly stores
 * the attribute name but leaves the rest up to subclasses.
 */
public abstract class BaseAttribute implements Attribute {
    /**
     * {@code non-null;} attribute name
     */
    private final String name;

    /**
     * Constructs an instance.
     *
     * @param name {@code non-null;} attribute name
     */
    public BaseAttribute(String name) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }

        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return name;
    }
}
