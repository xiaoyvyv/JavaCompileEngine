

package com.xiaoyv.dx.merge;

import com.xiaoyv.dex.ClassDef;
import com.xiaoyv.dex.Dex;

import java.util.Comparator;

/**
 * Name and structure of a type. Used to order types such that each type is
 * preceded by its supertype and implemented interfaces.
 */
final class SortableType {
    public static final Comparator<SortableType> NULLS_LAST_ORDER = new Comparator<SortableType>() {
        public int compare(SortableType a, SortableType b) {
            if (a == b) {
                return 0;
            }
            if (b == null) {
                return -1;
            }
            if (a == null) {
                return 1;
            }
            if (a.depth != b.depth) {
                return a.depth - b.depth;
            }
            return a.getTypeIndex() - b.getTypeIndex();
        }
    };

    private final Dex dex;
    private final IndexMap indexMap;
    private ClassDef classDef;
    private int depth = -1;

    public SortableType(Dex dex, IndexMap indexMap, ClassDef classDef) {
        this.dex = dex;
        this.indexMap = indexMap;
        this.classDef = classDef;
    }

    public Dex getDex() {
        return dex;
    }

    public IndexMap getIndexMap() {
        return indexMap;
    }

    public ClassDef getClassDef() {
        return classDef;
    }

    public int getTypeIndex() {
        return classDef.getTypeIndex();
    }

    /**
     * Assigns this type's depth if the depths of its supertype and implemented
     * interfaces are known. Returns false if the depth couldn't be computed
     * yet.
     */
    public boolean tryAssignDepth(SortableType[] types) {
        int max;
        if (classDef.getSupertypeIndex() == ClassDef.NO_INDEX) {
            max = 0; // this is Object.class or an interface
        } else {
            SortableType sortableSupertype = types[classDef.getSupertypeIndex()];
            if (sortableSupertype == null) {
                max = 1; // unknown, so assume it's a root.
            } else if (sortableSupertype.depth == -1) {
                return false;
            } else {
                max = sortableSupertype.depth;
            }
        }

        for (short interfaceIndex : classDef.getInterfaces()) {
            SortableType implemented = types[interfaceIndex];
            if (implemented == null) {
                max = Math.max(max, 1); // unknown, so assume it's a root.
            } else if (implemented.depth == -1) {
                return false;
            } else {
                max = Math.max(max, implemented.depth);
            }
        }

        depth = max + 1;
        return true;
    }

    public boolean isDepthAssigned() {
        return depth != -1;
    }
}
