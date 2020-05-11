

package com.xiaoyv.dx.rop.code;

import com.xiaoyv.dx.rop.type.StdTypeList;
import com.xiaoyv.dx.rop.type.Type;
import com.xiaoyv.dx.rop.type.TypeList;
import com.xiaoyv.dx.util.IntList;

/**
 * Instruction which contains switch cases.
 */
public final class SwitchInsn
        extends Insn {
    /**
     * {@code non-null;} list of switch cases
     */
    private final IntList cases;

    /**
     * Constructs an instance.
     *
     * @param opcode   {@code non-null;} the opcode
     * @param position {@code non-null;} source position
     * @param result   {@code null-ok;} spec for the result, if any
     * @param sources  {@code non-null;} specs for all the sources
     * @param cases    {@code non-null;} list of switch cases
     */
    public SwitchInsn(Rop opcode, SourcePosition position, RegisterSpec result,
                      RegisterSpecList sources, IntList cases) {
        super(opcode, position, result, sources);

        if (opcode.getBranchingness() != Rop.BRANCH_SWITCH) {
            throw new IllegalArgumentException("bogus branchingness");
        }

        if (cases == null) {
            throw new NullPointerException("cases == null");
        }

        this.cases = cases;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInlineString() {
        return cases.toString();
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
        visitor.visitSwitchInsn(this);
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
        return new SwitchInsn(getOpcode(), getPosition(),
                getResult().withOffset(delta),
                getSources().withOffset(delta),
                cases);
    }

    /**
     * {@inheritDoc}
     *
     * <p> SwitchInsn always compares false. The current use for this method
     * never encounters {@code SwitchInsn}s
     */
    @Override
    public boolean contentEquals(Insn b) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Insn withNewRegisters(RegisterSpec result,
                                 RegisterSpecList sources) {

        return new SwitchInsn(getOpcode(), getPosition(),
                result,
                sources,
                cases);
    }

    /**
     * Gets the list of switch cases.
     *
     * @return {@code non-null;} the case list
     */
    public IntList getCases() {
        return cases;
    }
}
