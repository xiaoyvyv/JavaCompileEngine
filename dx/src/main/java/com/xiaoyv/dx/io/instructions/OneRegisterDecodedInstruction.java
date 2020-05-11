

package com.xiaoyv.dx.io.instructions;

import com.xiaoyv.dx.io.IndexType;

/**
 * A decoded Dalvik instruction which has one register argument.
 */
public final class OneRegisterDecodedInstruction extends DecodedInstruction {
    /**
     * register argument "A"
     */
    private final int a;

    /**
     * Constructs an instance.
     */
    public OneRegisterDecodedInstruction(InstructionCodec format, int opcode,
                                         int index, IndexType indexType, int target, long literal,
                                         int a) {
        super(format, opcode, index, indexType, target, literal);

        this.a = a;
    }

    /**
     * @inheritDoc
     */
    public int getRegisterCount() {
        return 1;
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
        return new OneRegisterDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral(), a);
    }
}
