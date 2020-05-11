

package com.xiaoyv.dx.rop.code;

import com.xiaoyv.dx.rop.cst.Constant;
import com.xiaoyv.dx.rop.cst.CstString;
import com.xiaoyv.dx.rop.type.Type;
import com.xiaoyv.dx.rop.type.TypeList;

/**
 * Instruction which contains an explicit reference to a constant
 * and which might throw an exception.
 */
public final class ThrowingCstInsn
        extends CstInsn {
    /**
     * {@code non-null;} list of exceptions caught
     */
    private final TypeList catches;

    /**
     * Constructs an instance.
     *
     * @param opcode   {@code non-null;} the opcode
     * @param position {@code non-null;} source position
     * @param sources  {@code non-null;} specs for all the sources
     * @param catches  {@code non-null;} list of exceptions caught
     * @param cst      {@code non-null;} the constant
     */
    public ThrowingCstInsn(Rop opcode, SourcePosition position,
                           RegisterSpecList sources,
                           TypeList catches, Constant cst) {
        super(opcode, position, null, sources, cst);

        if (opcode.getBranchingness() != Rop.BRANCH_THROW) {
            throw new IllegalArgumentException("bogus branchingness");
        }

        if (catches == null) {
            throw new NullPointerException("catches == null");
        }

        this.catches = catches;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInlineString() {
        Constant cst = getConstant();
        String constantString = cst.toHuman();
        if (cst instanceof CstString) {
            constantString = ((CstString) cst).toQuoted();
        }
        return constantString + " " + ThrowingInsn.toCatchString(catches);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeList getCatches() {
        return catches;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visitThrowingCstInsn(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Insn withAddedCatch(Type type) {
        return new ThrowingCstInsn(getOpcode(), getPosition(),
                getSources(), catches.withAddedType(type),
                getConstant());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Insn withRegisterOffset(int delta) {
        return new ThrowingCstInsn(getOpcode(), getPosition(),
                getSources().withOffset(delta),
                catches,
                getConstant());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Insn withNewRegisters(RegisterSpec result,
                                 RegisterSpecList sources) {

        return new ThrowingCstInsn(getOpcode(), getPosition(),
                sources,
                catches,
                getConstant());
    }


}
