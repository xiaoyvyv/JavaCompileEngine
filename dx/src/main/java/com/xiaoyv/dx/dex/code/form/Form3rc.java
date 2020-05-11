

package com.xiaoyv.dx.dex.code.form;

import com.xiaoyv.dx.dex.code.CstInsn;
import com.xiaoyv.dx.dex.code.DalvInsn;
import com.xiaoyv.dx.dex.code.InsnFormat;
import com.xiaoyv.dx.rop.code.RegisterSpecList;
import com.xiaoyv.dx.rop.cst.Constant;
import com.xiaoyv.dx.rop.cst.CstMethodRef;
import com.xiaoyv.dx.rop.cst.CstType;
import com.xiaoyv.dx.util.AnnotatedOutput;

/**
 * Instruction format {@code 3rc}. See the instruction format spec
 * for details.
 */
public final class Form3rc extends InsnFormat {
    /**
     * {@code non-null;} unique instance of this class
     */
    public static final InsnFormat THE_ONE = new Form3rc();

    /**
     * Constructs an instance. This class is not publicly
     * instantiable. Use {@link #THE_ONE}.
     */
    private Form3rc() {
        // This space intentionally left blank.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnArgString(DalvInsn insn) {
        return regRangeString(insn.getRegisters()) + ", " +
                cstString(insn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnCommentString(DalvInsn insn, boolean noteIndices) {
        if (noteIndices) {
            return cstComment(insn);
        } else {
            return "";
        }
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
        if (!(insn instanceof CstInsn)) {
            return false;
        }

        CstInsn ci = (CstInsn) insn;
        int cpi = ci.getIndex();
        Constant cst = ci.getConstant();

        if (!unsignedFitsInShort(cpi)) {
            return false;
        }

        if (!((cst instanceof CstMethodRef) ||
                (cst instanceof CstType))) {
            return false;
        }

        RegisterSpecList regs = ci.getRegisters();
        int sz = regs.size();

        return (regs.size() == 0) ||
                (isRegListSequential(regs) &&
                        unsignedFitsInShort(regs.get(0).getReg()) &&
                        unsignedFitsInByte(regs.getWordCount()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTo(AnnotatedOutput out, DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        int cpi = ((CstInsn) insn).getIndex();
        int firstReg = (regs.size() == 0) ? 0 : regs.get(0).getReg();
        int count = regs.getWordCount();

        write(out, opcodeUnit(insn, count), (short) cpi, (short) firstReg);
    }
}
