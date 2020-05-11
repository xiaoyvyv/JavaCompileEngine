

package com.xiaoyv.dex;

import com.xiaoyv.dex.util.Unsigned;

public final class ProtoId implements Comparable<ProtoId> {
    private final Dex dex;
    private final int shortyIndex;
    private final int returnTypeIndex;
    private final int parametersOffset;

    public ProtoId(Dex dex, int shortyIndex, int returnTypeIndex, int parametersOffset) {
        this.dex = dex;
        this.shortyIndex = shortyIndex;
        this.returnTypeIndex = returnTypeIndex;
        this.parametersOffset = parametersOffset;
    }

    public int compareTo(ProtoId other) {
        if (returnTypeIndex != other.returnTypeIndex) {
            return Unsigned.compare(returnTypeIndex, other.returnTypeIndex);
        }
        return Unsigned.compare(parametersOffset, other.parametersOffset);
    }

    public int getShortyIndex() {
        return shortyIndex;
    }

    public int getReturnTypeIndex() {
        return returnTypeIndex;
    }

    public int getParametersOffset() {
        return parametersOffset;
    }

    public void writeTo(Dex.Section out) {
        out.writeInt(shortyIndex);
        out.writeInt(returnTypeIndex);
        out.writeInt(parametersOffset);
    }

    @Override
    public String toString() {
        if (dex == null) {
            return shortyIndex + " " + returnTypeIndex + " " + parametersOffset;
        }

        return dex.strings().get(shortyIndex)
                + ": " + dex.typeNames().get(returnTypeIndex)
                + " " + dex.readTypeList(parametersOffset);
    }
}
