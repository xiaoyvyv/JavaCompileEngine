

package com.xiaoyv.dex;

import com.xiaoyv.dex.util.Unsigned;

public final class FieldId implements Comparable<FieldId> {
    private final Dex dex;
    private final int declaringClassIndex;
    private final int typeIndex;
    private final int nameIndex;

    public FieldId(Dex dex, int declaringClassIndex, int typeIndex, int nameIndex) {
        this.dex = dex;
        this.declaringClassIndex = declaringClassIndex;
        this.typeIndex = typeIndex;
        this.nameIndex = nameIndex;
    }

    public int getDeclaringClassIndex() {
        return declaringClassIndex;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int compareTo(FieldId other) {
        if (declaringClassIndex != other.declaringClassIndex) {
            return Unsigned.compare(declaringClassIndex, other.declaringClassIndex);
        }
        if (nameIndex != other.nameIndex) {
            return Unsigned.compare(nameIndex, other.nameIndex);
        }
        return Unsigned.compare(typeIndex, other.typeIndex); // should always be 0
    }

    public void writeTo(Dex.Section out) {
        out.writeUnsignedShort(declaringClassIndex);
        out.writeUnsignedShort(typeIndex);
        out.writeInt(nameIndex);
    }

    @Override
    public String toString() {
        if (dex == null) {
            return declaringClassIndex + " " + typeIndex + " " + nameIndex;
        }
        return dex.typeNames().get(typeIndex) + "." + dex.strings().get(nameIndex);
    }
}
