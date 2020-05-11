

package com.xiaoyv.dx.cf.iface;

/**
 * Interface representing attributes of class files (directly or indirectly).
 */
public interface Attribute {
    /**
     * Get the name of the attribute.
     *
     * @return {@code non-null;} the name
     */
    public String getName();

    /**
     * Get the total length of the attribute in bytes, including the
     * header. Since the header is always six bytes, the result of
     * this method is always at least {@code 6}.
     *
     * @return {@code >= 6;} the total length, in bytes
     */
    public int byteLength();
}
