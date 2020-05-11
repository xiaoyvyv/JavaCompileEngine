

package com.xiaoyv.dx.util;

/**
 * An iterator for a list of ints.
 */
public interface IntIterator {

    /**
     * Checks to see if the iterator has a next value.
     *
     * @return true if next() will succeed
     */
    boolean hasNext();

    /**
     * Returns the next value in the iterator.
     *
     * @return next value
     * @throws java.util.NoSuchElementException if no next element exists
     */
    int next();
}
