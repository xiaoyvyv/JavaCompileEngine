

package com.xiaoyv.dx.cf.code;

import com.xiaoyv.dx.util.Hex;
import com.xiaoyv.dx.util.LabeledList;

/**
 * List of {@link ByteBlock} instances.
 */
public final class ByteBlockList extends LabeledList {

    /**
     * Constructs an instance.
     *
     * @param size {@code >= 0;} the number of elements to be in the list
     */
    public ByteBlockList(int size) {
        super(size);
    }

    /**
     * Gets the indicated element. It is an error to call this with the
     * index for an element which was never set; if you do that, this
     * will throw {@code NullPointerException}.
     *
     * @param n {@code >= 0, < size();} which element
     * @return {@code non-null;} the indicated element
     */
    public ByteBlock get(int n) {
        return (ByteBlock) get0(n);
    }

    /**
     * Gets the block with the given label.
     *
     * @param label the label to look for
     * @return {@code non-null;} the block with the given label
     */
    public ByteBlock labelToBlock(int label) {
        int idx = indexOfLabel(label);

        if (idx < 0) {
            throw new IllegalArgumentException("no such label: "
                    + Hex.u2(label));
        }

        return get(idx);
    }

    /**
     * Sets the element at the given index.
     *
     * @param n  {@code >= 0, < size();} which element
     * @param bb {@code null-ok;} the value to store
     */
    public void set(int n, ByteBlock bb) {
        super.set(n, bb);
    }
}
