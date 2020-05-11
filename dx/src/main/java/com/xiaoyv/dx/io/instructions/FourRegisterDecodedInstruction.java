

package com.xiaoyv.dx.io.instructions;

import com.xiaoyv.dx.io.IndexType;

/**
 * A decoded Dalvik instruction which has five register arguments.
 */
public final class FourRegisterDecodedInstruction extends DecodedInstruction {
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
     * register argument "D"
     */
    private final int d;

    /**
     * Constructs an instance.
     */
    public FourRegisterDecodedInstruction(InstructionCodec format, int opcode,
                                          int index, IndexType indexType, int target, long literal,
                                          int a, int b, int c, int d) {
        super(format, opcode, index, indexType, target, literal);

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * @inheritDoc
     */
    public int getRegisterCount() {
        return 4;
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
    public int getD() {
        return d;
    }

    /**
     * @inheritDoc
     */
    public DecodedInstruction withIndex(int newIndex) {
        return new FourRegisterDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral(), a, b, c, d);
    }
}
