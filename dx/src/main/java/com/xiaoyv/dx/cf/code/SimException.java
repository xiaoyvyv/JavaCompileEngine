

package com.xiaoyv.dx.cf.code;

import com.xiaoyv.dex.util.ExceptionWithContext;

/**
 * Exception from simulation.
 */
public class SimException
        extends ExceptionWithContext {
    public SimException(String message) {
        super(message);
    }

    public SimException(Throwable cause) {
        super(cause);
    }

    public SimException(String message, Throwable cause) {
        super(message, cause);
    }
}
