

package com.xiaoyv.dex;

import com.xiaoyv.dex.util.Unsigned;

public final class TypeList implements Comparable<TypeList> {

    public static final TypeList EMPTY = new TypeList(null, Dex.EMPTY_SHORT_ARRAY);

    private final Dex dex;
    private final short[] types;

    public TypeList(Dex dex, short[] types) {
        this.dex = dex;
        this.types = types;
    }

    public short[] getTypes() {
        return types;
    }

    @Override
    public int compareTo(TypeList other) {
        for (int i = 0; i < types.length && i < other.types.length; i++) {
            if (types[i] != other.types[i]) {
                return Unsigned.compare(types[i], other.types[i]);
            }
        }
        return Unsigned.compare(types.length, other.types.length);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        for (int i = 0, typesLength = types.length; i < typesLength; i++) {
            result.append(dex != null ? dex.typeNames().get(types[i]) : types[i]);
        }
        result.append(")");
        return result.toString();
    }
}
