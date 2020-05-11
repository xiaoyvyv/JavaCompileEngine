

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.cf.code.LocalVariableList;

/**
 * Attribute class for standard {@code LocalVariableTable} attributes.
 */
public final class AttLocalVariableTable extends BaseLocalVariables {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME = "LocalVariableTable";

    /**
     * Constructs an instance.
     *
     * @param localVariables {@code non-null;} list of local variable entries
     */
    public AttLocalVariableTable(LocalVariableList localVariables) {
        super(ATTRIBUTE_NAME, localVariables);
    }
}
