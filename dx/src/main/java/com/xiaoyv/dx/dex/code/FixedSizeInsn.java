

package com.xiaoyv.dx.dex.code;

import com.xiaoyv.dx.rop.code.RegisterSpecList;
import com.xiaoyv.dx.rop.code.SourcePosition;
import com.xiaoyv.dx.util.AnnotatedOutput;

/**
 * Base class for instructions which are of a fixed code size and
 * which use {@link InsnFormat} methods to write themselves. This
 * includes most &mdash; but not all &mdash; instructions.
 */
public abstract class FixedSizeInsn extends DalvInsn {
    /**
     * Constructs an instance. The output address of this instance is initially
     * unknown ({@code -1}).
     *
     * <p><b>Note:</b> In the unlikely event that an instruction takes
     * absolutely no registers (e.g., a {@code nop} or a
     * no-argument no-result * static method call), then the given
     * register list may be passed as {@link
     * RegisterSpecList#EMPTY}.</p>
     *
     * @param opcode    the opcode; one of the constants from {@link Dops}
     * @param position  {@code non-null;} source position
     * @param registers {@code non-null;} register list, including a
     *                  result register if appropriate (that is, registers may be either
     *                  ins or outs)
     */
    public FixedSizeInsn(Dop opcode, SourcePosition position,
                         RegisterSpecList registers) {
        super(opcode, position, registers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int codeSize() {
        return getOpcode().getFormat().codeSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void writeTo(AnnotatedOutput out) {
        getOpcode().getFormat().writeTo(out, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final DalvInsn withRegisterOffset(int delta) {
        return withRegisters(getRegisters().withOffset(delta));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final String listingString0(boolean noteIndices) {
        return getOpcode().getFormat().listingString(this, noteIndices);
    }
}
