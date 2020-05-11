

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.rop.cst.CstString;

/**
 * Attribute class for standard {@code SourceFile} attributes.
 */
public final class AttSourceFile extends BaseAttribute {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME = "SourceFile";

    /**
     * {@code non-null;} name of the source file
     */
    private final CstString sourceFile;

    /**
     * Constructs an instance.
     *
     * @param sourceFile {@code non-null;} the name of the source file
     */
    public AttSourceFile(CstString sourceFile) {
        super(ATTRIBUTE_NAME);

        if (sourceFile == null) {
            throw new NullPointerException("sourceFile == null");
        }

        this.sourceFile = sourceFile;
    }

    /**
     * {@inheritDoc}
     */
    public int byteLength() {
        return 8;
    }

    /**
     * Gets the source file name of this instance.
     *
     * @return {@code non-null;} the source file
     */
    public CstString getSourceFile() {
        return sourceFile;
    }
}
