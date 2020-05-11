

package com.xiaoyv.dx.io.instructions;

/**
 * Base implementation of {@link CodeCursor}.
 */
public abstract class BaseCodeCursor implements CodeCursor {
    /**
     * base address map
     */
    private final AddressMap baseAddressMap;

    /**
     * next index within {@link #array} to read from or write to
     */
    private int cursor;

    /**
     * Constructs an instance.
     */
    public BaseCodeCursor() {
        this.baseAddressMap = new AddressMap();
        this.cursor = 0;
    }

    /**
     * @inheritDoc
     */
    public final int cursor() {
        return cursor;
    }

    /**
     * @inheritDoc
     */
    public final int baseAddressForCursor() {
        int mapped = baseAddressMap.get(cursor);
        return (mapped >= 0) ? mapped : cursor;
    }

    /**
     * @inheritDoc
     */
    public final void setBaseAddress(int targetAddress, int baseAddress) {
        baseAddressMap.put(targetAddress, baseAddress);
    }

    /**
     * Advance the cursor by the indicated amount.
     */
    protected final void advance(int amount) {
        cursor += amount;
    }
}
