

package com.xiaoyv.dx.dex.code;

import com.xiaoyv.dx.rop.code.RegisterSpecList;
import com.xiaoyv.dx.rop.code.SourcePosition;

/**
 * Instruction which has no extra info beyond the basics provided for in
 * the base class.
 */
public final class SimpleInsn extends FixedSizeInsn {
    /**
     * Constructs an instance. The output address of this instance is initially
     * unknown ({@code -1}).
     *
     * @param opcode    the opcode; one of the constants from {@link Dops}
     * @param position  {@code non-null;} source position
     * @param registers {@code non-null;} register list, including a
     *                  result register if appropriate (that is, registers may be either
     *                  ins or outs)
     */
    public SimpleInsn(Dop opcode, SourcePosition position,
                      RegisterSpecList registers) {
        super(opcode, position, registers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DalvInsn withOpcode(Dop opcode) {
        return new SimpleInsn(opcode, getPosition(), getRegisters());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DalvInsn withRegisters(RegisterSpecList registers) {
        return new SimpleInsn(getOpcode(), getPosition(), registers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String argString() {
        return null;
    }
}
