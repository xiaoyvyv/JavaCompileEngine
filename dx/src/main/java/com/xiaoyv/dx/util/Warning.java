

package com.xiaoyv.dx.util;

/**
 * Exception which is meant to indicate a non-fatal warning.
 */
public class Warning extends RuntimeException {
    /**
     * Constructs an instance.
     *
     * @param message human-oriented message
     */
    public Warning(String message) {
        super(message);
    }
}
