

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.cf.code.LocalVariableList;

/**
 * Attribute class for standard {@code LocalVariableTypeTable} attributes.
 */
public final class AttLocalVariableTypeTable extends BaseLocalVariables {
    /**
     * {@code non-null;} attribute name for attributes of this type
     */
    public static final String ATTRIBUTE_NAME = "LocalVariableTypeTable";

    /**
     * Constructs an instance.
     *
     * @param localVariables {@code non-null;} list of local variable entries
     */
    public AttLocalVariableTypeTable(LocalVariableList localVariables) {
        super(ATTRIBUTE_NAME, localVariables);
    }
}
