

package com.xiaoyv.dx.rop.code;

/**
 * Implementation of {@link TranslationAdvice} which conservatively answers
 * {@code false} to all methods.
 */
public final class ConservativeTranslationAdvice
        implements TranslationAdvice {
    /**
     * {@code non-null;} standard instance of this class
     */
    public static final ConservativeTranslationAdvice THE_ONE =
            new ConservativeTranslationAdvice();

    /**
     * This class is not publicly instantiable. Use {@link #THE_ONE}.
     */
    private ConservativeTranslationAdvice() {
        // This space intentionally left blank.
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasConstantOperation(Rop opcode,
                                        RegisterSpec sourceA, RegisterSpec sourceB) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean requiresSourcesInOrder(Rop opcode,
                                          RegisterSpecList sources) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public int getMaxOptimalRegisterCount() {
        return Integer.MAX_VALUE;
    }
}
