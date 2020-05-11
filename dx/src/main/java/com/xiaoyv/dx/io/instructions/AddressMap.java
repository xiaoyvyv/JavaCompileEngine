

package com.xiaoyv.dx.io.instructions;

import java.util.HashMap;

/**
 * Map from addresses to addresses, where addresses are all
 * {@code int}s.
 */
public final class AddressMap {
    /**
     * underlying map. TODO: This might be too inefficient.
     */
    private final HashMap<Integer, Integer> map;

    /**
     * Constructs an instance.
     */
    public AddressMap() {
        map = new HashMap<Integer, Integer>();
    }

    /**
     * Gets the value address corresponding to the given key address. Returns
     * {@code -1} if there is no mapping.
     */
    public int get(int keyAddress) {
        Integer value = map.get(keyAddress);
        return (value == null) ? -1 : value;
    }

    /**
     * Sets the value address associated with the given key address.
     */
    public void put(int keyAddress, int valueAddress) {
        map.put(keyAddress, valueAddress);
    }
}
