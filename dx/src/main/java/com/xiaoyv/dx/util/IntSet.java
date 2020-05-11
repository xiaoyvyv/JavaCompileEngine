

package com.xiaoyv.dx.util;

/**
 * A set of integers
 */
public interface IntSet {

    /**
     * Adds an int to a set
     *
     * @param value int to add
     */
    void add(int value);

    /**
     * Removes an int from a set.
     *
     * @param value int to remove
     */
    void remove(int value);

    /**
     * Checks to see if a value is in the set
     *
     * @param value int to check
     * @return true if in set
     */
    boolean has(int value);

    /**
     * Merges {@code other} into this set, so this set becomes the
     * union of the two.
     *
     * @param other {@code non-null;} other set to merge with.
     */
    void merge(IntSet other);

    /**
     * Returns the count of unique elements in this set.
     *
     * @return {@code > = 0;} count of unique elements
     */
    int elements();

    /**
     * Iterates the set
     *
     * @return {@code non-null;} a set iterator
     */
    IntIterator iterator();
}
