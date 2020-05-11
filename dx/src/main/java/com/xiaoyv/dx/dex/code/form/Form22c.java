

package com.xiaoyv.dx.dex.code.form;

import com.xiaoyv.dx.dex.code.CstInsn;
import com.xiaoyv.dx.dex.code.DalvInsn;
import com.xiaoyv.dx.dex.code.InsnFormat;
import com.xiaoyv.dx.rop.code.RegisterSpecList;
import com.xiaoyv.dx.rop.cst.Constant;
import com.xiaoyv.dx.rop.cst.CstFieldRef;
import com.xiaoyv.dx.rop.cst.CstType;
import com.xiaoyv.dx.util.AnnotatedOutput;

import java.util.BitSet;

/**
 * Instruction format {@code 22c}. See the instruction format spec
 * for details.
 */
public final class Form22c extends InsnFormat {
    /**
     * {@code non-null;} unique instance of this class
     */
    public static final InsnFormat THE_ONE = new Form22c();

    /**
     * Constructs an instance. This class is not publicly
     * instantiable. Use {@link #THE_ONE}.
     */
    private Form22c() {
        // This space intentionally left blank.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnArgString(DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        return regs.get(0).regString() + ", " + regs.get(1).regString() +
                ", " + cstString(insn);
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
        return 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCompatible(DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        if (!((insn instanceof CstInsn) &&
                (regs.size() == 2) &&
                unsignedFitsInNibble(regs.get(0).getReg()) &&
                unsignedFitsInNibble(regs.get(1).getReg()))) {
            return false;
        }

        CstInsn ci = (CstInsn) insn;
        int cpi = ci.getIndex();

        if (!unsignedFitsInShort(cpi)) {
            return false;
        }

        Constant cst = ci.getConstant();
        return (cst instanceof CstType) ||
                (cst instanceof CstFieldRef);
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
    public void writeTo(AnnotatedOutput out, DalvInsn insn) {
        RegisterSpecList regs = insn.getRegisters();
        int cpi = ((CstInsn) insn).getIndex();

        write(out,
                opcodeUnit(insn,
                        makeByte(regs.get(0).getReg(), regs.get(1).getReg())),
                (short) cpi);
    }
}
