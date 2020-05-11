

package com.xiaoyv.dex;

import com.xiaoyv.dex.util.ByteArrayByteInput;
import com.xiaoyv.dex.util.ByteInput;

/**
 * An encoded value or array.
 */
public final class EncodedValue implements Comparable<EncodedValue> {
    private final byte[] data;

    public EncodedValue(byte[] data) {
        this.data = data;
    }

    public ByteInput asByteInput() {
        return new ByteArrayByteInput(data);
    }

    public byte[] getBytes() {
        return data;
    }

    public void writeTo(Dex.Section out) {
        out.write(data);
    }

    @Override
    public int compareTo(EncodedValue other) {
        int size = Math.min(data.length, other.data.length);
        for (int i = 0; i < size; i++) {
            if (data[i] != other.data[i]) {
                return (data[i] & 0xff) - (other.data[i] & 0xff);
            }
        }
        return data.length - other.data.length;
    }

    @Override
    public String toString() {
        return Integer.toHexString(data[0] & 0xff) + "...(" + data.length + ")";
    }
}
