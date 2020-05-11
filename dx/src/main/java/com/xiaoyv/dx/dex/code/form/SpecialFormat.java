

package com.xiaoyv.dx.dex.code.form;

import com.xiaoyv.dx.dex.code.DalvInsn;
import com.xiaoyv.dx.dex.code.InsnFormat;
import com.xiaoyv.dx.util.AnnotatedOutput;

/**
 * Instruction format for nonstandard format instructions, which aren't
 * generally real instructions but do end up appearing in instruction
 * lists. Most of the overridden methods on this class end up throwing
 * exceptions, as code should know (implicitly or explicitly) to avoid
 * using this class. The one exception is {@link #isCompatible}, which
 * always returns {@code true}.
 */
public final class SpecialFormat extends InsnFormat {
    /**
     * {@code non-null;} unique instance of this class
     */
    public static final InsnFormat THE_ONE = new SpecialFormat();

    /**
     * Constructs an instance. This class is not publicly
     * instantiable. Use {@link #THE_ONE}.
     */
    private SpecialFormat() {
        // This space intentionally left blank.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnArgString(DalvInsn insn) {
        throw new RuntimeException("unsupported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String insnCommentString(DalvInsn insn, boolean noteIndices) {
        throw new RuntimeException("unsupported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int codeSize() {
        throw new RuntimeException("unsupported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCompatible(DalvInsn insn) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTo(AnnotatedOutput out, DalvInsn insn) {
        throw new RuntimeException("unsupported");
    }
}
