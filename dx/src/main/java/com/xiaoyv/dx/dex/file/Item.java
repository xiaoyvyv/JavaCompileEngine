

package com.xiaoyv.dx.dex.file;

import com.xiaoyv.dx.util.AnnotatedOutput;

/**
 * Base class for any structurally-significant and (potentially)
 * repeated piece of a Dalvik file.
 */
public abstract class Item {
    /**
     * Constructs an instance.
     */
    public Item() {
        // This space intentionally left blank.
    }

    /**
     * Returns the item type for this instance.
     *
     * @return {@code non-null;} the item type
     */
    public abstract ItemType itemType();

    /**
     * Returns the human name for the particular type of item this
     * instance is.
     *
     * @return {@code non-null;} the name
     */
    public final String typeName() {
        return itemType().toHuman();
    }

    /**
     * Gets the size of this instance when written, in bytes.
     *
     * @return {@code >= 0;} the write size
     */
    public abstract int writeSize();

    /**
     * Populates a {@link DexFile} with items from within this instance.
     * This will <i>not</i> add an item to the file for this instance itself
     * (which should have been done by whatever refers to this instance).
     *
     * <p><b>Note:</b> Subclasses must override this to do something
     * appropriate.</p>
     *
     * @param file {@code non-null;} the file to populate
     */
    public abstract void addContents(DexFile file);

    /**
     * Writes the representation of this instance to the given data section,
     * using the given {@link DexFile} to look things up as needed.
     * If this instance keeps track of its offset, then this method will
     * note the written offset and will also throw an exception if this
     * instance has already been written.
     *
     * @param file {@code non-null;} the file to use for reference
     * @param out  {@code non-null;} where to write to
     */
    public abstract void writeTo(DexFile file, AnnotatedOutput out);
}
