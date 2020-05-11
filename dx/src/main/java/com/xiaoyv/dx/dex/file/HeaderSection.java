

package com.xiaoyv.dx.dex.file;

import com.xiaoyv.dx.rop.cst.Constant;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * File header section of a {@code .dex} file.
 */
public final class HeaderSection extends UniformItemSection {
    /**
     * {@code non-null;} the list of the one item in the section
     */
    private final List<HeaderItem> list;

    /**
     * Constructs an instance. The file offset is initially unknown.
     *
     * @param file {@code non-null;} file that this instance is part of
     */
    public HeaderSection(DexFile file) {
        super(null, file, 4);

        HeaderItem item = new HeaderItem();
        item.setIndex(0);

        this.list = Collections.singletonList(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndexedItem get(Constant cst) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends Item> items() {
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void orderItems() {
        // Nothing to do here.
    }
}
