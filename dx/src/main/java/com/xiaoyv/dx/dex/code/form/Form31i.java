

package com.xiaoyv.dx.dex.code.form;

import com.xiaoyv.dx.dex.code.CstInsn;
import com.xiaoyv.dx.dex.code.DalvInsn;
import com.xiaoyv.dx.dex.code.InsnFormat;
import com.xiaoyv.dx.rop.code.RegisterSpecList;
import com.xiaoyv.dx.rop.cst.Constant;
import com.xiaoyv.dx.rop.cst.CstLiteralBits;
import com.xiaoyv.dx.util.AnnotatedOutput;

import java.util.BitSet;

/**
 * Instruction format {@code 31i}. See the instruction format spec
 * for details.
 */
public final class Form31i extends InsnFormat {
    /**
     * {@code non-null;} unique instance of this class
     */
    public static final InsnFormat THE_ONE = new Form31i();

    /**
     * Constructs an instance. This class is not publicly
     * instantiable. Use {@link #THE_ONE}.
     */
    private Form31i() {
        // This space intentionally left blank.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnArgString(DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        CstLiteralBits value = (CstLiteralBits) ((CstInsn) insn).getConstant();

        return regs.get(0).regString() + ", " + literalBitsString(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnCommentString(DalvInsn insn, boolean noteIndices) {
        CstLiteralBits value = (CstLiteralBits) ((CstInsn) insn).getConstant();
        return literalBitsComment(value, 32);
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
        if (!((insn instanceof CstInsn) &&
                (regs.size() == 1) &&
                unsignedFitsInByte(regs.get(0).getReg()))) {
            return false;
        }

        CstInsn ci = (CstInsn) insn;
        Constant cst = ci.getConstant();

        if (!(cst instanceof CstLiteralBits)) {
            return false;
        }

        return ((CstLiteralBits) cst).fitsInInt();
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
        int value =
                ((CstLiteralBits) ((CstInsn) insn).getConstant()).getIntBits();

        write(out, opcodeUnit(insn, regs.get(0).getReg()), value);
    }
}
