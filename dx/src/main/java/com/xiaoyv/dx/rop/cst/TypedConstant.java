

package com.xiaoyv.dx.rop.cst;

import com.xiaoyv.dx.rop.type.TypeBearer;

/**
 * Base class for constants which implement {@link TypeBearer}.
 */
public abstract class TypedConstant
        extends Constant implements TypeBearer {
    /**
     * {@inheritDoc}
     * <p>
     * This implementation always returns {@code this}.
     */
    public final TypeBearer getFrameType() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public final int getBasicType() {
        return getType().getBasicType();
    }

    /**
     * {@inheritDoc}
     */
    public final int getBasicFrameType() {
        return getType().getBasicFrameType();
    }

    /**
     * {@inheritDoc}
     */
    public final boolean isConstant() {
        return true;
    }
}
