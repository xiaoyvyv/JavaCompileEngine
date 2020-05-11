

package com.xiaoyv.dex.util;

public final class ByteArrayByteInput implements ByteInput {

    private final byte[] bytes;
    private int position;

    public ByteArrayByteInput(byte... bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte readByte() {
        return bytes[position++];
    }
}
