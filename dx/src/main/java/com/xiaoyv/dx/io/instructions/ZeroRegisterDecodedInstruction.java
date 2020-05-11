

package com.xiaoyv.dx.io.instructions;

import com.xiaoyv.dx.io.IndexType;

/**
 * A decoded Dalvik instruction which has no register arguments.
 */
public final class ZeroRegisterDecodedInstruction extends DecodedInstruction {
    /**
     * Constructs an instance.
     */
    public ZeroRegisterDecodedInstruction(InstructionCodec format, int opcode,
                                          int index, IndexType indexType, int target, long literal) {
        super(format, opcode, index, indexType, target, literal);
    }

    /**
     * @inheritDoc
     */
    public int getRegisterCount() {
        return 0;
    }

    /**
     * @inheritDoc
     */
    public DecodedInstruction withIndex(int newIndex) {
        return new ZeroRegisterDecodedInstruction(
                getFormat(), getOpcode(), newIndex, getIndexType(),
                getTarget(), getLiteral());
    }
}
