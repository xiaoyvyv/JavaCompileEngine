

package com.xiaoyv.dx.dex.code.form;

import com.xiaoyv.dx.dex.code.DalvInsn;
import com.xiaoyv.dx.dex.code.InsnFormat;
import com.xiaoyv.dx.dex.code.TargetInsn;
import com.xiaoyv.dx.rop.code.RegisterSpecList;
import com.xiaoyv.dx.util.AnnotatedOutput;

import java.util.BitSet;

/**
 * Instruction format {@code 31t}. See the instruction format spec
 * for details.
 */
public final class Form31t extends InsnFormat {
    /**
     * {@code non-null;} unique instance of this class
     */
    public static final InsnFormat THE_ONE = new Form31t();

    /**
     * Constructs an instance. This class is not publicly
     * instantiable. Use {@link #THE_ONE}.
     */
    private Form31t() {
        // This space intentionally left blank.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnArgString(DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        return regs.get(0).regString() + ", " + branchString(insn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnCommentString(DalvInsn insn, boolean noteIndices) {
        return branchComment(insn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int codeSize() {
        return 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCompatible(DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();

        if (!((insn instanceof TargetInsn) &&
                (regs.size() == 1) &&
                unsignedFitsInByte(regs.get(0).getReg()))) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BitSet compatibleRegs(DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        BitSet bits = new BitSet(1);

        bits.set(0, unsignedFitsInByte(regs.get(0).getReg()));
        return bits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean branchFits(TargetInsn insn) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTo(AnnotatedOutput out, DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        int offset = ((TargetInsn) insn).getTargetOffset();

        write(out, opcodeUnit(insn, regs.get(0).getReg()), offset);
    }
}
