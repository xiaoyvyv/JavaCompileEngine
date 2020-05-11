

package com.xiaoyv.dx.dex.code;

import com.xiaoyv.dx.rop.type.Type;

import java.util.HashSet;

/**
 * Interface for the construction of {@link CatchTable} instances.
 */
public interface CatchBuilder {
    /**
     * Builds and returns the catch table for this instance.
     *
     * @return {@code non-null;} the constructed table
     */
    public CatchTable build();

    /**
     * Gets whether this instance has any catches at all (either typed
     * or catch-all).
     *
     * @return whether this instance has any catches at all
     */
    public boolean hasAnyCatches();

    /**
     * Gets the set of catch types associated with this instance.
     *
     * @return {@code non-null;} the set of catch types
     */
    public HashSet<Type> getCatchTypes();
}
