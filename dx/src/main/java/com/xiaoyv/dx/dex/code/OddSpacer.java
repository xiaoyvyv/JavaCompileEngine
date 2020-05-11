

package com.xiaoyv.dx.dex.code;

import com.xiaoyv.dx.io.Opcodes;
import com.xiaoyv.dx.rop.code.RegisterSpecList;
import com.xiaoyv.dx.rop.code.SourcePosition;
import com.xiaoyv.dx.util.AnnotatedOutput;

/**
 * Pseudo-instruction which either turns into a {@code nop} or
 * nothingness, in order to make the subsequent instruction have an
 * even address. This is used to align (subsequent) instructions that
 * require it.
 */
public final class OddSpacer extends VariableSizeInsn {
    /**
     * Constructs an instance. The output address of this instance is initially
     * unknown ({@code -1}).
     *
     * @param position {@code non-null;} source position
     */
    public OddSpacer(SourcePosition position) {
        super(position, RegisterSpecList.EMPTY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int codeSize() {
        return (getAddress() & 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTo(AnnotatedOutput out) {
        if (codeSize() != 0) {
            out.writeShort(InsnFormat.codeUnit(Opcodes.NOP, 0));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DalvInsn withRegisters(RegisterSpecList registers) {
        return new OddSpacer(getPosition());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String argString() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String listingString0(boolean noteIndices) {
        if (codeSize() == 0) {
            return null;
        }

        return "nop // spacer";
    }
}
