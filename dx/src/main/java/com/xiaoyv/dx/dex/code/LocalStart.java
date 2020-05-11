

package com.xiaoyv.dx.dex.code;

import com.xiaoyv.dx.rop.code.RegisterSpec;
import com.xiaoyv.dx.rop.code.RegisterSpecList;
import com.xiaoyv.dx.rop.code.SourcePosition;
import com.xiaoyv.dx.ssa.RegisterMapper;

/**
 * Pseudo-instruction which is used to introduce a new local variable. That
 * is, an instance of this class in an instruction stream indicates that
 * starting with the subsequent instruction, the indicated variable
 * is bound.
 */
public final class LocalStart extends ZeroSizeInsn {
    /**
     * {@code non-null;} register spec representing the local variable introduced
     * by this instance
     */
    private final RegisterSpec local;

    /**
     * Returns the local variable listing string for a single register spec.
     *
     * @param spec {@code non-null;} the spec to convert
     * @return {@code non-null;} the string form
     */
    public static String localString(RegisterSpec spec) {
        return spec.regString() + ' ' + spec.getLocalItem().toString() + ": " +
                spec.getTypeBearer().toHuman();
    }

    /**
     * Constructs an instance. The output address of this instance is initially
     * unknown ({@code -1}).
     *
     * @param position {@code non-null;} source position
     * @param local    {@code non-null;} register spec representing the local
     *                 variable introduced by this instance
     */
    public LocalStart(SourcePosition position, RegisterSpec local) {
        super(position);

        if (local == null) {
            throw new NullPointerException("local == null");
        }

        this.local = local;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DalvInsn withRegisterOffset(int delta) {
        return new LocalStart(getPosition(), local.withOffset(delta));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DalvInsn withRegisters(RegisterSpecList registers) {
        return new LocalStart(getPosition(), local);
    }

    /**
     * Gets the register spec representing the local variable introduced
     * by this instance.
     *
     * @return {@code non-null;} the register spec
     */
    public RegisterSpec getLocal() {
        return local;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String argString() {
        return local.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String listingString0(boolean noteIndices) {
        return "local-start " + localString(local);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DalvInsn withMapper(RegisterMapper mapper) {
        return new LocalStart(getPosition(), mapper.map(local));
    }
}
