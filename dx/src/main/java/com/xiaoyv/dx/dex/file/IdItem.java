

package com.xiaoyv.dx.dex.file;

import com.xiaoyv.dx.rop.cst.CstType;

/**
 * Representation of a reference to an item inside a Dalvik file.
 */
public abstract class IdItem extends IndexedItem {
    /**
     * {@code non-null;} the type constant for the defining class of
     * the reference
     */
    private final CstType type;

    /**
     * Constructs an instance.
     *
     * @param type {@code non-null;} the type constant for the defining
     *             class of the reference
     */
    public IdItem(CstType type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addContents(DexFile file) {
        TypeIdsSection typeIds = file.getTypeIds();
        typeIds.intern(type);
    }

    /**
     * Gets the type constant for the defining class of the
     * reference.
     *
     * @return {@code non-null;} the type constant
     */
    public final CstType getDefiningClass() {
        return type;
    }
}
