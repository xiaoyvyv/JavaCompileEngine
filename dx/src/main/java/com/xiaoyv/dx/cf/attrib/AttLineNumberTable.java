

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.cf.code.LineNumberList;
import com.xiaoyv.dx.util.MutabilityException;

/**
 * Attribute class for standard {@code LineNumberTable} attributes.
 */
public final class AttLineNumberTable extends BaseAttribute {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME = "LineNumberTable";

    /**
     * {@code non-null;} list of line number entries
     */
    private final LineNumberList lineNumbers;

    /**
     * Constructs an instance.
     *
     * @param lineNumbers {@code non-null;} list of line number entries
     */
    public AttLineNumberTable(LineNumberList lineNumbers) {
        super(ATTRIBUTE_NAME);

        try {
            if (lineNumbers.isMutable()) {
                throw new MutabilityException("lineNumbers.isMutable()");
            }
        } catch (NullPointerException ex) {
            // Translate the exception.
            throw new NullPointerException("lineNumbers == null");
        }

        this.lineNumbers = lineNumbers;
    }

    /**
     * {@inheritDoc}
     */
    public int byteLength() {
        return 8 + 4 * lineNumbers.size();
    }

    /**
     * Gets the list of "line number" entries associated with this instance.
     *
     * @return {@code non-null;} the list
     */
    public LineNumberList getLineNumbers() {
        return lineNumbers;
    }
}
