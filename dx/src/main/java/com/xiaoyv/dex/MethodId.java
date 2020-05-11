

package com.xiaoyv.dex;

import com.xiaoyv.dex.util.Unsigned;

public final class MethodId implements Comparable<MethodId> {
    private final Dex dex;
    private final int declaringClassIndex;
    private final int protoIndex;
    private final int nameIndex;

    public MethodId(Dex dex, int declaringClassIndex, int protoIndex, int nameIndex) {
        this.dex = dex;
        this.declaringClassIndex = declaringClassIndex;
        this.protoIndex = protoIndex;
        this.nameIndex = nameIndex;
    }

    public int getDeclaringClassIndex() {
        return declaringClassIndex;
    }

    public int getProtoIndex() {
        return protoIndex;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int compareTo(MethodId other) {
        if (declaringClassIndex != other.declaringClassIndex) {
            return Unsigned.compare(declaringClassIndex, other.declaringClassIndex);
        }
        if (nameIndex != other.nameIndex) {
            return Unsigned.compare(nameIndex, other.nameIndex);
        }
        return Unsigned.compare(protoIndex, other.protoIndex);
    }

    public void writeTo(Dex.Section out) {
        out.writeUnsignedShort(declaringClassIndex);
        out.writeUnsignedShort(protoIndex);
        out.writeInt(nameIndex);
    }

    @Override
    public String toString() {
        if (dex == null) {
            return declaringClassIndex + " " + protoIndex + " " + nameIndex;
        }
        return dex.typeNames().get(declaringClassIndex)
                + "." + dex.strings().get(nameIndex)
                + dex.readTypeList(dex.protoIds().get(protoIndex).getParametersOffset());
    }
}
