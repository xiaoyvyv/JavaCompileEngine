

package com.xiaoyv.dx.dex.code.form;

import com.xiaoyv.dx.dex.code.DalvInsn;
import com.xiaoyv.dx.dex.code.InsnFormat;
import com.xiaoyv.dx.dex.code.SimpleInsn;
import com.xiaoyv.dx.util.AnnotatedOutput;

/**
 * Instruction format {@code 10x}. See the instruction format spec
 * for details.
 */
public final class Form10x extends InsnFormat {
    /**
     * {@code non-null;} unique instance of this class
     */
    public static final InsnFormat THE_ONE = new Form10x();

    /**
     * Constructs an instance. This class is not publicly
     * instantiable. Use {@link #THE_ONE}.
     */
    private Form10x() {
        // This space intentionally left blank.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnArgString(DalvInsn insn) {
        // This format has no arguments.
        return "";
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
        return (insn instanceof SimpleInsn) &&
                (insn.getRegisters().size() == 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTo(AnnotatedOutput out, DalvInsn insn) {
        write(out, opcodeUnit(insn, 0));
    }
}
