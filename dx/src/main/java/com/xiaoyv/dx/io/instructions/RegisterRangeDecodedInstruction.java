

package com.xiaoyv.dx.io.instructions;

import com.xiaoyv.dx.io.IndexType;

/**
 * A decoded Dalvik instruction which has register range arguments (an
 * "A" start register and a register count).
 */
public final class RegisterRangeDecodedInstruction extends DecodedInstruction {
    /**
     * register argument "A"
     */
    private final int a;

    /**
     * register count
     */
    private final int registerCount;

    /**
     * Constructs an instance.
     */
    public RegisterRangeDecodedInstruction(InstructionCodec format, int opcode,
                                           int index, IndexType indexType, int target, long literal,
                                           int a, int registerCount) {
        super(format, opcode, index, indexType, target, literal);

        this.a = a;
        this.registerCount = registerCount;
    }

    /**
     * @inheritDoc
     */
    public int getRegisterCount() {
        return registerCount;
    }

    /**
     * @inheritDoc
     */
    public int getA() {
        return a;
    }

    /**
     * @inheritDoc
     */
    public DecodedInstruction withIndex(int newIndex) {
        return new RegisterRangeDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral(), a, registerCount);
    }
}
