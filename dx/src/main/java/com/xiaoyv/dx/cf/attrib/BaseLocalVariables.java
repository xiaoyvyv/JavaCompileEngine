

package com.xiaoyv.dx.cf.attrib;

import com.xiaoyv.dx.cf.code.LocalVariableList;
import com.xiaoyv.dx.util.MutabilityException;

/**
 * Base attribute class for standard {@code LocalVariableTable}
 * and {@code LocalVariableTypeTable} attributes.
 */
public abstract class BaseLocalVariables extends BaseAttribute {
    /**
     * {@code non-null;} list of local variable entries
     */
    private final LocalVariableList localVariables;

    /**
     * Constructs an instance.
     *
     * @param name           {@code non-null;} attribute name
     * @param localVariables {@code non-null;} list of local variable entries
     */
    public BaseLocalVariables(String name,
                              LocalVariableList localVariables) {
        super(name);

        try {
            if (localVariables.isMutable()) {
                throw new MutabilityException("localVariables.isMutable()");
            }
        } catch (NullPointerException ex) {
            // Translate the exception.
            throw new NullPointerException("localVariables == null");
        }

        this.localVariables = localVariables;
    }

    /**
     * {@inheritDoc}
     */
    public final int byteLength() {
        return 8 + localVariables.size() * 10;
    }

    /**
     * Gets the list of "local variable" entries associated with this instance.
     *
     * @return {@code non-null;} the list
     */
    public final LocalVariableList getLocalVariables() {
        return localVariables;
    }
}
