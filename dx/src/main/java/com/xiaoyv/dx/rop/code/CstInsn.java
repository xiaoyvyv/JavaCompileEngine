

package com.xiaoyv.dx.rop.code;

import com.xiaoyv.dx.rop.cst.Constant;

/**
 * Instruction which contains an explicit reference to a constant.
 */
public abstract class CstInsn
        extends Insn {
    /**
     * {@code non-null;} the constant
     */
    private final Constant cst;

    /**
     * Constructs an instance.
     *
     * @param opcode   {@code non-null;} the opcode
     * @param position {@code non-null;} source position
     * @param result   {@code null-ok;} spec for the result, if any
     * @param sources  {@code non-null;} specs for all the sources
     * @param cst      {@code non-null;} constant
     */
    public CstInsn(Rop opcode, SourcePosition position, RegisterSpec result,
                   RegisterSpecList sources, Constant cst) {
        super(opcode, position, result, sources);

        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        this.cst = cst;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInlineString() {
        return cst.toHuman();
    }

    /**
     * Gets the constant.
     *
     * @return {@code non-null;} the constant
     */
    public Constant getConstant() {
        return cst;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contentEquals(Insn b) {
        /*
         * The cast (CstInsn)b below should always succeed since
         * Insn.contentEquals compares classes of this and b.
         */
        return super.contentEquals(b)
                && cst.equals(((CstInsn) b).getConstant());
    }
}
