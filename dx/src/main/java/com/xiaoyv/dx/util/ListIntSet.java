

package com.xiaoyv.dx.util;

import java.util.NoSuchElementException;

/**
 * A set of integers, represented by a list
 */
public class ListIntSet implements IntSet {

    /**
     * also accessed in BitIntSet
     */
    final IntList ints;

    /**
     * Constructs an instance
     */
    public ListIntSet() {
        ints = new IntList();
        ints.sort();
    }

    /**
     * @inheritDoc
     */
    public void add(int value) {
        int index = ints.binarysearch(value);

        if (index < 0) {
            ints.insert(-(index + 1), value);
        }
    }

    /**
     * @inheritDoc
     */
    public void remove(int value) {
        int index = ints.indexOf(value);

        if (index >= 0) {
            ints.removeIndex(index);
        }
    }

    /**
     * @inheritDoc
     */
    public boolean has(int value) {
        return ints.indexOf(value) >= 0;
    }

    /**
     * @inheritDoc
     */
    public void merge(IntSet other) {
        if (other instanceof ListIntSet) {
            ListIntSet o = (ListIntSet) other;
            int szThis = ints.size();
            int szOther = o.ints.size();

            int i = 0;
            int j = 0;

            while (j < szOther && i < szThis) {
                while (j < szOther && o.ints.get(j) < ints.get(i)) {
                    add(o.ints.get(j++));
                }
                if (j == szOther) {
                    break;
                }
                while (i < szThis && o.ints.get(j) >= ints.get(i)) {
                    i++;
                }
            }

            while (j < szOther) {
                add(o.ints.get(j++));
            }

            ints.sort();
        } else if (other instanceof BitIntSet) {
            BitIntSet o = (BitIntSet) other;

            for (int i = 0; i >= 0; i = Bits.findFirst(o.bits, i + 1)) {
                ints.add(i);
            }
            ints.sort();
        } else {
            IntIterator iter = other.iterator();
            while (iter.hasNext()) {
                add(iter.next());
            }
        }
    }

    /**
     * @inheritDoc
     */
    public int elements() {
        return ints.size();
    }

    /**
     * @inheritDoc
     */
    public IntIterator iterator() {
        return new IntIterator() {
            private int idx = 0;

            /** @inheritDoc */
            public boolean hasNext() {
                return idx < ints.size();
            }

            /** @inheritDoc */
            public int next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return ints.get(idx++);
            }
        };
    }

    /**
     * @inheritDoc
     */
    public String toString() {
        return ints.toString();
    }
}
