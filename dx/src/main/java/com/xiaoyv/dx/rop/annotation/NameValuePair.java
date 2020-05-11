

package com.xiaoyv.dx.rop.annotation;

import com.xiaoyv.dx.rop.cst.Constant;
import com.xiaoyv.dx.rop.cst.CstString;

/**
 * A (name, value) pair. These are used as the contents of an annotation.
 */
public final class NameValuePair implements Comparable<NameValuePair> {
    /**
     * {@code non-null;} the name
     */
    private final CstString name;

    /**
     * {@code non-null;} the value
     */
    private final Constant value;

    /**
     * Construct an instance.
     *
     * @param name  {@code non-null;} the name
     * @param value {@code non-null;} the value
     */
    public NameValuePair(CstString name, Constant value) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }

        if (value == null) {
            throw new NullPointerException("value == null");
        }

        this.name = name;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return name.toHuman() + ":" + value;
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return name.hashCode() * 31 + value.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object other) {
        if (!(other instanceof NameValuePair)) {
            return false;
        }

        NameValuePair otherPair = (NameValuePair) other;

        return name.equals(otherPair.name)
                && value.equals(otherPair.value);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Instances of this class compare in name-major and value-minor
     * order.</p>
     */
    public int compareTo(NameValuePair other) {
        int result = name.compareTo(other.name);

        if (result != 0) {
            return result;
        }

        return value.compareTo(other.value);
    }

    /**
     * Gets the name.
     *
     * @return {@code non-null;} the name
     */
    public CstString getName() {
        return name;
    }

    /**
     * Gets the value.
     *
     * @return {@code non-null;} the value
     */
    public Constant getValue() {
        return value;
    }
}
