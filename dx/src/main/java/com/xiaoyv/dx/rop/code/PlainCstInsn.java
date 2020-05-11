

package com.xiaoyv.dx.rop.code;

import com.xiaoyv.dx.rop.cst.Constant;
import com.xiaoyv.dx.rop.type.StdTypeList;
import com.xiaoyv.dx.rop.type.Type;
import com.xiaoyv.dx.rop.type.TypeList;

/**
 * Instruction which contains an explicit reference to a constant
 * but which cannot throw an exception.
 */
public final class PlainCstInsn
        extends CstInsn {
    /**
     * Constructs an instance.
     *
     * @param opcode   {@code non-null;} the opcode
     * @param position {@code non-null;} source position
     * @param result   {@code null-ok;} spec for the result, if any
     * @param sources  {@code non-null;} specs for all the sources
     * @param cst      {@code non-null;} the constant
     */
    public PlainCstInsn(Rop opcode, SourcePosition position,
                        RegisterSpec result, RegisterSpecList sources,
                        Constant cst) {
        super(opcode, position, result, sources, cst);

        if (opcode.getBranchingness() != Rop.BRANCH_NONE) {
            throw new IllegalArgumentException("bogus branchingness");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeList getCatches() {
        return StdTypeList.EMPTY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visitPlainCstInsn(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Insn withAddedCatch(Type type) {
        throw new UnsupportedOperationException("unsupported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Insn withRegisterOffset(int delta) {
        return new PlainCstInsn(getOpcode(), getPosition(),
                getResult().withOffset(delta),
                getSources().withOffset(delta),
                getConstant());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Insn withNewRegisters(RegisterSpec result,
                                 RegisterSpecList sources) {

        return new PlainCstInsn(getOpcode(), getPosition(),
                result,
                sources,
                getConstant());

    }
}
