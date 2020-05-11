

package com.xiaoyv.dx.dex.code;

import com.xiaoyv.dx.rop.code.RegisterSpecList;
import com.xiaoyv.dx.rop.code.SourcePosition;

/**
 * Pseudo-instruction base class for variable-sized instructions.
 */
public abstract class VariableSizeInsn extends DalvInsn {
    /**
     * Constructs an instance. The output address of this instance is initially
     * unknown ({@code -1}).
     *
     * @param position  {@code non-null;} source position
     * @param registers {@code non-null;} source registers
     */
    public VariableSizeInsn(SourcePosition position,
                            RegisterSpecList registers) {
        super(Dops.SPECIAL_FORMAT, position, registers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DalvInsn withOpcode(Dop opcode) {
        throw new RuntimeException("unsupported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DalvInsn withRegisterOffset(int delta) {
        return withRegisters(getRegisters().withOffset(delta));
    }
}
