

package com.xiaoyv.dx.io.instructions;

import com.xiaoyv.dx.io.IndexType;

/**
 * A decoded Dalvik instruction which has two register arguments.
 */
public final class TwoRegisterDecodedInstruction extends DecodedInstruction {
    /**
     * register argument "A"
     */
    private final int a;

    /**
     * register argument "B"
     */
    private final int b;

    /**
     * Constructs an instance.
     */
    public TwoRegisterDecodedInstruction(InstructionCodec format, int opcode,
                                         int index, IndexType indexType, int target, long literal,
                                         int a, int b) {
        super(format, opcode, index, indexType, target, literal);

        this.a = a;
        this.b = b;
    }

    /**
     * @inheritDoc
     */
    public int getRegisterCount() {
        return 2;
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
    public int getB() {
        return b;
    }

    /**
     * @inheritDoc
     */
    public DecodedInstruction withIndex(int newIndex) {
        return new TwoRegisterDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral(), a, b);
    }
}
