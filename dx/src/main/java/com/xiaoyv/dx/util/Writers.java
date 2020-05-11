

package com.xiaoyv.dx.util;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Utilities for dealing with {@code Writer}s.
 */
public final class Writers {
    /**
     * This class is uninstantiable.
     */
    private Writers() {
        // This space intentionally left blank.
    }

    /**
     * Makes a {@code PrintWriter} for the given {@code Writer},
     * returning the given writer if it already happens to be the right
     * class.
     *
     * @param writer {@code non-null;} writer to (possibly) wrap
     * @return {@code non-null;} an appropriate instance
     */
    public static PrintWriter printWriterFor(Writer writer) {
        if (writer instanceof PrintWriter) {
            return (PrintWriter) writer;
        }

        return new PrintWriter(writer);
    }
}
