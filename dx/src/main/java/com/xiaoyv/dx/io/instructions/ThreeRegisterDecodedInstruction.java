

package com.xiaoyv.dx.io.instructions;

import com.xiaoyv.dx.io.IndexType;

/**
 * A decoded Dalvik instruction which has three register arguments.
 */
public final class ThreeRegisterDecodedInstruction extends DecodedInstruction {
    /**
     * register argument "A"
     */
    private final int a;

    /**
     * register argument "B"
     */
    private final int b;

    /**
     * register argument "C"
     */
    private final int c;

    /**
     * Constructs an instance.
     */
    public ThreeRegisterDecodedInstruction(InstructionCodec format, int opcode,
                                           int index, IndexType indexType, int target, long literal,
                                           int a, int b, int c) {
        super(format, opcode, index, indexType, target, literal);

        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * @inheritDoc
     */
    public int getRegisterCount() {
        return 3;
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
    public int getC() {
        return c;
    }

    /**
     * @inheritDoc
     */
    public DecodedInstruction withIndex(int newIndex) {
        return new ThreeRegisterDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral(), a, b, c);
    }
}
