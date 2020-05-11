

package com.xiaoyv.dx.util;

import com.xiaoyv.dex.util.ExceptionWithContext;

/**
 * Exception due to a mutability problem.
 */
public class MutabilityException
        extends ExceptionWithContext {
    public MutabilityException(String message) {
        super(message);
    }

    public MutabilityException(Throwable cause) {
        super(cause);
    }

    public MutabilityException(String message, Throwable cause) {
        super(message, cause);
    }
}
