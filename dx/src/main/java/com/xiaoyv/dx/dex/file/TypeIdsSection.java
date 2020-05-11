

package com.xiaoyv.dx.dex.file;

import com.xiaoyv.dex.DexFormat;
import com.xiaoyv.dex.DexIndexOverflowException;
import com.xiaoyv.dx.command.dexer.Main;
import com.xiaoyv.dx.rop.cst.Constant;
import com.xiaoyv.dx.rop.cst.CstType;
import com.xiaoyv.dx.rop.type.Type;
import com.xiaoyv.dx.util.AnnotatedOutput;
import com.xiaoyv.dx.util.Hex;

import java.util.Collection;
import java.util.TreeMap;

/**
 * Type identifiers list section of a {@code .dex} file.
 */
public final class TypeIdsSection extends UniformItemSection {
    /**
     * {@code non-null;} map from types to {@link TypeIdItem} instances
     */
    private final TreeMap<Type, TypeIdItem> typeIds;

    /**
     * Constructs an instance. The file offset is initially unknown.
     *
     * @param file {@code non-null;} file that this instance is part of
     */
    public TypeIdsSection(DexFile file) {
        super("type_ids", file, 4);

        typeIds = new TreeMap<Type, TypeIdItem>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends Item> items() {
        return typeIds.values();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndexedItem get(Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        throwIfNotPrepared();

        Type type = ((CstType) cst).getClassType();
        IndexedItem result = typeIds.get(type);

        if (result == null) {
            throw new IllegalArgumentException("not found: " + cst);
        }

        return result;
    }

    /**
     * Writes the portion of the file header that refers to this instance.
     *
     * @param out {@code non-null;} where to write
     */
    public void writeHeaderPart(AnnotatedOutput out) {
        throwIfNotPrepared();

        int sz = typeIds.size();
        int offset = (sz == 0) ? 0 : getFileOffset();

        if (sz > DexFormat.MAX_TYPE_IDX + 1) {
            throw new DexIndexOverflowException("Too many type references: " + sz +
                    "; max is " + (DexFormat.MAX_TYPE_IDX + 1) + ".\n" +
                    Main.getTooManyIdsErrorMessage());
        }

        if (out.annotates()) {
            out.annotate(4, "type_ids_size:   " + Hex.u4(sz));
            out.annotate(4, "type_ids_off:    " + Hex.u4(offset));
        }

        out.writeInt(sz);
        out.writeInt(offset);
    }

    /**
     * Interns an element into this instance.
     *
     * @param type {@code non-null;} the type to intern
     * @return {@code non-null;} the interned reference
     */
    public synchronized TypeIdItem intern(Type type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        throwIfPrepared();

        TypeIdItem result = typeIds.get(type);

        if (result == null) {
            result = new TypeIdItem(new CstType(type));
            typeIds.put(type, result);
        }

        return result;
    }

    /**
     * Interns an element into this instance.
     *
     * @param type {@code non-null;} the type to intern
     * @return {@code non-null;} the interned reference
     */
    public synchronized TypeIdItem intern(CstType type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        throwIfPrepared();

        Type typePerSe = type.getClassType();
        TypeIdItem result = typeIds.get(typePerSe);

        if (result == null) {
            result = new TypeIdItem(type);
            typeIds.put(typePerSe, result);
        }

        return result;
    }

    /**
     * Gets the index of the given type, which must have
     * been added to this instance.
     *
     * @param type {@code non-null;} the type to look up
     * @return {@code >= 0;} the reference's index
     */
    public int indexOf(Type type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        throwIfNotPrepared();

        TypeIdItem item = typeIds.get(type);

        if (item == null) {
            throw new IllegalArgumentException("not found: " + type);
        }

        return item.getIndex();
    }

    /**
     * Gets the index of the given type, which must have
     * been added to this instance.
     *
     * @param type {@code non-null;} the type to look up
     * @return {@code >= 0;} the reference's index
     */
    public int indexOf(CstType type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        return indexOf(type.getClassType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void orderItems() {
        int idx = 0;

        for (Object i : items()) {
            ((TypeIdItem) i).setIndex(idx);
            idx++;
        }
    }
}
