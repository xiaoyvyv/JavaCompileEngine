

package com.xiaoyv.dex;

import com.xiaoyv.dex.util.ExceptionWithContext;

/**
 * Thrown when there's a format problem reading, writing, or generally
 * processing a dex file.
 */
public class DexException extends ExceptionWithContext {
    public DexException(String message) {
        super(message);
    }

    public DexException(Throwable cause) {
        super(cause);
    }
}
