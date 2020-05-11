

package com.xiaoyv.dx.dex.code.form;

import com.xiaoyv.dx.dex.code.DalvInsn;
import com.xiaoyv.dx.dex.code.InsnFormat;
import com.xiaoyv.dx.dex.code.TargetInsn;
import com.xiaoyv.dx.rop.code.RegisterSpecList;
import com.xiaoyv.dx.util.AnnotatedOutput;

import java.util.BitSet;

/**
 * Instruction format {@code 22t}. See the instruction format spec
 * for details.
 */
public final class Form22t extends InsnFormat {
    /**
     * {@code non-null;} unique instance of this class
     */
    public static final InsnFormat THE_ONE = new Form22t();

    /**
     * Constructs an instance. This class is not publicly
     * instantiable. Use {@link #THE_ONE}.
     */
    private Form22t() {
        // This space intentionally left blank.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnArgString(DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        return regs.get(0).regString() + ", " + regs.get(1).regString() +
                ", " + branchString(insn);
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
        return 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCompatible(DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();

        if (!((insn instanceof TargetInsn) &&
                (regs.size() == 2) &&
                unsignedFitsInNibble(regs.get(0).getReg()) &&
                unsignedFitsInNibble(regs.get(1).getReg()))) {
            return false;
        }

        TargetInsn ti = (TargetInsn) insn;
        return ti.hasTargetOffset() ? branchFits(ti) : true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BitSet compatibleRegs(DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        BitSet bits = new BitSet(2);

        bits.set(0, unsignedFitsInNibble(regs.get(0).getReg()));
        bits.set(1, unsignedFitsInNibble(regs.get(1).getReg()));
        return bits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean branchFits(TargetInsn insn) {
        int offset = insn.getTargetOffset();

        // Note: A zero offset would fit, but it is prohibited by the spec.
        return (offset != 0) && signedFitsInShort(offset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTo(AnnotatedOutput out, DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        int offset = ((TargetInsn) insn).getTargetOffset();

        write(out,
                opcodeUnit(insn,
                        makeByte(regs.get(0).getReg(), regs.get(1).getReg())),
                (short) offset);
    }
}
