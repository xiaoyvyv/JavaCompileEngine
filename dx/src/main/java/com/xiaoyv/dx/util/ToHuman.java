

package com.xiaoyv.dx.util;

/**
 * Simple interface for objects that can return a "human" (as opposed to
 * a complete but often hard to read) string form.
 */
public interface ToHuman {
    /**
     * Return the "human" string form of this instance.  This is
     * generally less "debuggy" than {@code toString()}.
     *
     * @return {@code non-null;} the human string form
     */
    String toHuman();
}
