

package com.xiaoyv.dx.dex.file;

import com.xiaoyv.dx.rop.cst.CstBaseMethodRef;

/**
 * Representation of a method reference inside a Dalvik file.
 */
public final class MethodIdItem extends MemberIdItem {
    /**
     * Constructs an instance.
     *
     * @param method {@code non-null;} the constant for the method
     */
    public MethodIdItem(CstBaseMethodRef method) {
        super(method);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemType itemType() {
        return ItemType.TYPE_METHOD_ID_ITEM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addContents(DexFile file) {
        super.addContents(file);

        ProtoIdsSection protoIds = file.getProtoIds();
        protoIds.intern(getMethodRef().getPrototype());
    }

    /**
     * Gets the method constant.
     *
     * @return {@code non-null;} the constant
     */
    public CstBaseMethodRef getMethodRef() {
        return (CstBaseMethodRef) getRef();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getTypoidIdx(DexFile file) {
        ProtoIdsSection protoIds = file.getProtoIds();
        return protoIds.indexOf(getMethodRef().getPrototype());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTypoidName() {
        return "proto_idx";
    }
}
