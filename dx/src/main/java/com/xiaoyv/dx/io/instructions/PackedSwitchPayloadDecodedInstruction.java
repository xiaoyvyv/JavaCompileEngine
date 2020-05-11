

package com.xiaoyv.dx.io.instructions;

/**
 * A decoded Dalvik instruction which contains the payload for
 * a {@code packed-switch} instruction.
 */
public final class PackedSwitchPayloadDecodedInstruction
        extends DecodedInstruction {
    /**
     * first key value
     */
    private final int firstKey;

    /**
     * array of target addresses. These are absolute, not relative,
     * addresses.
     */
    private final int[] targets;

    /**
     * Constructs an instance.
     */
    public PackedSwitchPayloadDecodedInstruction(InstructionCodec format,
                                                 int opcode, int firstKey, int[] targets) {
        super(format, opcode, 0, null, 0, 0L);

        this.firstKey = firstKey;
        this.targets = targets;
    }

    /**
     * @inheritDoc
     */
    public int getRegisterCount() {
        return 0;
    }

    public int getFirstKey() {
        return firstKey;
    }

    public int[] getTargets() {
        return targets;
    }

    /**
     * @inheritDoc
     */
    public DecodedInstruction withIndex(int newIndex) {
        throw new UnsupportedOperationException("no index in instruction");
    }
}
