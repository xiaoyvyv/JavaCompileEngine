

package com.xiaoyv.dx.cf.iface;

import com.xiaoyv.dx.rop.type.Prototype;

/**
 * Interface representing methods of class files.
 */
public interface Method
        extends Member {
    /**
     * Get the <i>effective</i> method descriptor, which includes, if
     * necessary, a first {@code this} parameter.
     *
     * @return {@code non-null;} the effective method descriptor
     */
    public Prototype getEffectiveDescriptor();
}
