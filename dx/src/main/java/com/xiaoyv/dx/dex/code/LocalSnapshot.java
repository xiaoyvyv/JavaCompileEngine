

package com.xiaoyv.dx.dex.code;

import com.xiaoyv.dx.rop.code.RegisterSpec;
import com.xiaoyv.dx.rop.code.RegisterSpecList;
import com.xiaoyv.dx.rop.code.RegisterSpecSet;
import com.xiaoyv.dx.rop.code.SourcePosition;
import com.xiaoyv.dx.ssa.RegisterMapper;

/**
 * Pseudo-instruction which is used to hold a snapshot of the
 * state of local variable name mappings that exists immediately after
 * the instance in an instruction array.
 */
public final class LocalSnapshot extends ZeroSizeInsn {
    /**
     * {@code non-null;} local state associated with this instance
     */
    private final RegisterSpecSet locals;

    /**
     * Constructs an instance. The output address of this instance is initially
     * unknown ({@code -1}).
     *
     * @param position {@code non-null;} source position
     * @param locals   {@code non-null;} associated local variable state
     */
    public LocalSnapshot(SourcePosition position, RegisterSpecSet locals) {
        super(position);

        if (locals == null) {
            throw new NullPointerException("locals == null");
        }

        this.locals = locals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DalvInsn withRegisterOffset(int delta) {
        return new LocalSnapshot(getPosition(), locals.withOffset(delta));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DalvInsn withRegisters(RegisterSpecList registers) {
        return new LocalSnapshot(getPosition(), locals);
    }

    /**
     * Gets the local state associated with this instance.
     *
     * @return {@code non-null;} the state
     */
    public RegisterSpecSet getLocals() {
        return locals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String argString() {
        return locals.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String listingString0(boolean noteIndices) {
        int sz = locals.size();
        int max = locals.getMaxSize();
        StringBuffer sb = new StringBuffer(100 + sz * 40);

        sb.append("local-snapshot");

        for (int i = 0; i < max; i++) {
            RegisterSpec spec = locals.get(i);
            if (spec != null) {
                sb.append("\n  ");
                sb.append(LocalStart.localString(spec));
            }
        }

        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DalvInsn withMapper(RegisterMapper mapper) {
        return new LocalSnapshot(getPosition(), mapper.map(locals));
    }
}
