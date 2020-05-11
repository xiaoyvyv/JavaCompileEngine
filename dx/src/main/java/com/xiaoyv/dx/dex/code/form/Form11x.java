

package com.xiaoyv.dx.dex.code.form;

import com.xiaoyv.dx.dex.code.DalvInsn;
import com.xiaoyv.dx.dex.code.InsnFormat;
import com.xiaoyv.dx.dex.code.SimpleInsn;
import com.xiaoyv.dx.rop.code.RegisterSpecList;
import com.xiaoyv.dx.util.AnnotatedOutput;

import java.util.BitSet;

/**
 * Instruction format {@code 11x}. See the instruction format spec
 * for details.
 */
public final class Form11x extends InsnFormat {
    /**
     * {@code non-null;} unique instance of this class
     */
    public static final InsnFormat THE_ONE = new Form11x();

    /**
     * Constructs an instance. This class is not publicly
     * instantiable. Use {@link #THE_ONE}.
     */
    private Form11x() {
        // This space intentionally left blank.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnArgString(DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        return regs.get(0).regString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnCommentString(DalvInsn insn, boolean noteIndices) {
        // This format has no comment.
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int codeSize() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCompatible(DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        return (insn instanceof SimpleInsn) &&
                (regs.size() == 1) &&
                unsignedFitsInByte(regs.get(0).getReg());
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
    public void writeTo(AnnotatedOutput out, DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        write(out, opcodeUnit(insn, regs.get(0).getReg()));
    }
}
