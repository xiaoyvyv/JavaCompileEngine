

package com.xiaoyv.dx.dex.file;

import com.xiaoyv.dx.rop.cst.CstFieldRef;

/**
 * Representation of a field reference inside a Dalvik file.
 */
public final class FieldIdItem extends MemberIdItem {
    /**
     * Constructs an instance.
     *
     * @param field {@code non-null;} the constant for the field
     */
    public FieldIdItem(CstFieldRef field) {
        super(field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemType itemType() {
        return ItemType.TYPE_FIELD_ID_ITEM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addContents(DexFile file) {
        super.addContents(file);

        TypeIdsSection typeIds = file.getTypeIds();
        typeIds.intern(getFieldRef().getType());
    }

    /**
     * Gets the field constant.
     *
     * @return {@code non-null;} the constant
     */
    public CstFieldRef getFieldRef() {
        return (CstFieldRef) getRef();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getTypoidIdx(DexFile file) {
        TypeIdsSection typeIds = file.getTypeIds();
        return typeIds.indexOf(getFieldRef().getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTypoidName() {
        return "type_idx";
    }
}
