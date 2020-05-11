

package com.xiaoyv.dx.cf.iface;

import com.xiaoyv.dx.util.FixedSizeList;

/**
 * Standard implementation of {@link AttributeList}, which directly stores
 * an array of {@link Attribute} objects and can be made immutable.
 */
public final class StdAttributeList extends FixedSizeList
        implements AttributeList {
    /**
     * Constructs an instance. All indices initially contain {@code null}.
     *
     * @param size the size of the list
     */
    public StdAttributeList(int size) {
        super(size);
    }

    /**
     * {@inheritDoc}
     */
    public Attribute get(int n) {
        return (Attribute) get0(n);
    }

    /**
     * {@inheritDoc}
     */
    public int byteLength() {
        int sz = size();
        int result = 2; // u2 attributes_count

        for (int i = 0; i < sz; i++) {
            result += get(i).byteLength();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    public Attribute findFirst(String name) {
        int sz = size();

        for (int i = 0; i < sz; i++) {
            Attribute att = get(i);
            if (att.getName().equals(name)) {
                return att;
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Attribute findNext(Attribute attrib) {
        int sz = size();
        int at;

        outer:
        {
            for (at = 0; at < sz; at++) {
                Attribute att = get(at);
                if (att == attrib) {
                    break outer;
                }
            }

            return null;
        }

        String name = attrib.getName();

        for (at++; at < sz; at++) {
            Attribute att = get(at);
            if (att.getName().equals(name)) {
                return att;
            }
        }

        return null;
    }

    /**
     * Sets the attribute at the given index.
     *
     * @param n         {@code >= 0, < size();} which attribute
     * @param attribute {@code null-ok;} the attribute object
     */
    public void set(int n, Attribute attribute) {
        set0(n, attribute);
    }
}
