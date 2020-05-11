

package com.xiaoyv.dx.cf.iface;

import com.xiaoyv.dex.util.ExceptionWithContext;

/**
 * Exception from parsing.
 */
public class ParseException
        extends ExceptionWithContext {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
