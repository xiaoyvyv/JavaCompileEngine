

package com.xiaoyv.dx.rop.annotation;

import com.xiaoyv.dx.util.FixedSizeList;

/**
 * List of {@link Annotations} instances.
 */
public final class AnnotationsList
        extends FixedSizeList {
    /**
     * {@code non-null;} immutable empty instance
     */
    public static final AnnotationsList EMPTY = new AnnotationsList(0);

    /**
     * Constructs an immutable instance which is the combination of
     * the two given instances. The two instances must each have the
     * same number of elements, and each pair of elements must contain
     * disjoint sets of types.
     *
     * @param list1 {@code non-null;} an instance
     * @param list2 {@code non-null;} the other instance
     * @return {@code non-null;} the combination
     */
    public static AnnotationsList combine(AnnotationsList list1,
                                          AnnotationsList list2) {
        int size = list1.size();

        if (size != list2.size()) {
            throw new IllegalArgumentException("list1.size() != list2.size()");
        }

        AnnotationsList result = new AnnotationsList(size);

        for (int i = 0; i < size; i++) {
            Annotations a1 = list1.get(i);
            Annotations a2 = list2.get(i);
            result.set(i, Annotations.combine(a1, a2));
        }

        result.setImmutable();
        return result;
    }

    /**
     * Constructs an instance. All indices initially contain {@code null}.
     *
     * @param size the size of the list
     */
    public AnnotationsList(int size) {
        super(size);
    }

    /**
     * Gets the element at the given index. It is an error to call
     * this with the index for an element which was never set; if you
     * do that, this will throw {@code NullPointerException}.
     *
     * @param n {@code >= 0, < size();} which index
     * @return {@code non-null;} element at that index
     */
    public Annotations get(int n) {
        return (Annotations) get0(n);
    }

    /**
     * Sets the element at the given index. The given element must be
     * immutable.
     *
     * @param n {@code >= 0, < size();} which index
     * @param a {@code null-ok;} the element to set at {@code n}
     */
    public void set(int n, Annotations a) {
        a.throwIfMutable();
        set0(n, a);
    }
}
