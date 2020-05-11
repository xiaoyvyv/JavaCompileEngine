

package com.xiaoyv.dx.dex.code.form;

import com.xiaoyv.dx.dex.code.DalvInsn;
import com.xiaoyv.dx.dex.code.InsnFormat;
import com.xiaoyv.dx.dex.code.TargetInsn;
import com.xiaoyv.dx.util.AnnotatedOutput;

/**
 * Instruction format {@code 20t}. See the instruction format spec
 * for details.
 */
public final class Form20t extends InsnFormat {
    /**
     * {@code non-null;} unique instance of this class
     */
    public static final InsnFormat THE_ONE = new Form20t();

    /**
     * Constructs an instance. This class is not publicly
     * instantiable. Use {@link #THE_ONE}.
     */
    private Form20t() {
        // This space intentionally left blank.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnArgString(DalvInsn insn) {
        return branchString(insn);
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
        if (!((insn instanceof TargetInsn) &&
                (insn.getRegisters().size() == 0))) {
            return false;
        }

        TargetInsn ti = (TargetInsn) insn;
        return ti.hasTargetOffset() ? branchFits(ti) : true;
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
        int offset = ((TargetInsn) insn).getTargetOffset();

        write(out, opcodeUnit(insn, 0), (short) offset);
    }
}
