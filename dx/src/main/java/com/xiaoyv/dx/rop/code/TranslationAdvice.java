

package com.xiaoyv.dx.rop.code;

/**
 * Interface for "advice" passed from the late stage of translation back
 * to the early stage. This allows for the final target architecture to
 * exert its influence early in the translation process without having
 * the early stage code be explicitly tied to the target.
 */
public interface TranslationAdvice {
    /**
     * Returns an indication of whether the target can directly represent an
     * instruction with the given opcode operating on the given arguments,
     * where the last source argument is used as a constant. (That is, the
     * last argument must have a type which indicates it is a known constant.)
     * The instruction associated must have exactly two sources.
     *
     * @param opcode  {@code non-null;} the opcode
     * @param sourceA {@code non-null;} the first source
     * @param sourceB {@code non-null;} the second source
     * @return {@code true} iff the target can represent the operation
     * using a constant for the last argument
     */
    public boolean hasConstantOperation(Rop opcode,
                                        RegisterSpec sourceA, RegisterSpec sourceB);

    /**
     * Returns true if the translation target requires the sources of the
     * specified opcode to be in order and contiguous (eg, for an invoke-range)
     *
     * @param opcode  {@code non-null;} opcode
     * @param sources {@code non-null;} source list
     * @return {@code true} iff the target requires the sources to be
     * in order and contiguous.
     */
    public boolean requiresSourcesInOrder(Rop opcode, RegisterSpecList sources);

    /**
     * Gets the maximum register width that can be represented optimally.
     * For example, Dex bytecode does not have instruction forms that take
     * register numbers larger than 15 for all instructions so
     * DexTranslationAdvice returns 15 here.
     *
     * @return register count noted above
     */
    public int getMaxOptimalRegisterCount();
}
