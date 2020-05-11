

package com.xiaoyv.dx.io.instructions;

/**
 * A decoded Dalvik instruction which contains the payload for
 * a {@code packed-switch} instruction.
 */
public final class SparseSwitchPayloadDecodedInstruction
        extends DecodedInstruction {
    /**
     * array of key values
     */
    private final int[] keys;

    /**
     * array of target addresses. These are absolute, not relative,
     * addresses.
     */
    private final int[] targets;

    /**
     * Constructs an instance.
     */
    public SparseSwitchPayloadDecodedInstruction(InstructionCodec format,
                                                 int opcode, int[] keys, int[] targets) {
        super(format, opcode, 0, null, 0, 0L);

        if (keys.length != targets.length) {
            throw new IllegalArgumentException("keys/targets length mismatch");
        }

        this.keys = keys;
        this.targets = targets;
    }

    /**
     * @inheritDoc
     */
    public int getRegisterCount() {
        return 0;
    }

    public int[] getKeys() {
        return keys;
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
