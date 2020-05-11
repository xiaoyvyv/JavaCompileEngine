

package com.xiaoyv.dx.ssa;

import com.xiaoyv.dx.util.BitIntSet;
import com.xiaoyv.dx.util.IntSet;
import com.xiaoyv.dx.util.ListIntSet;


/**
 * Makes int sets for various parts of the optimizer.
 */
public final class SetFactory {

    /**
     * BitIntSet/ListIntSet threshold for dominance frontier sets. These
     * sets are kept per basic block until phi placement and tend to be,
     * like the CFG itself, very sparse at large sizes.
     * <p>
     * A value of 3072 here is somewhere around 1.125mb of total bitset size.
     */
    private static final int DOMFRONT_SET_THRESHOLD_SIZE = 3072;

    /**
     * BitIntSet/ListIntSet threshold for interference graph sets. These
     * sets are kept per register until register allocation is done.
     * <p>
     * A value of 3072 here is somewhere around 1.125mb of total bitset size.
     */
    private static final int INTERFERENCE_SET_THRESHOLD_SIZE = 3072;

    /**
     * BitIntSet/ListIntSet threshold for the live in/out sets kept by
     * {@link SsaBasicBlock}. These are sets of SSA registers kept per basic
     * block during register allocation.
     * <p>
     * The total size of a bitset for this would be the count of blocks
     * times the size of registers. The threshold value here is merely
     * the register count, which is typically on the order of the block
     * count as well.
     */
    private static final int LIVENESS_SET_THRESHOLD_SIZE = 3072;


    /**
     * Make IntSet for the dominance-frontier sets.
     *
     * @param szBlocks {@code >=0;} count of basic blocks in method
     * @return {@code non-null;} appropriate set
     */
    /*package*/
    static IntSet makeDomFrontSet(int szBlocks) {
        return szBlocks <= DOMFRONT_SET_THRESHOLD_SIZE
                ? new BitIntSet(szBlocks)
                : new ListIntSet();
    }

    /**
     * Make IntSet for the interference graph sets. Public because
     * InterferenceGraph is in another package.
     *
     * @param countRegs {@code >=0;} count of SSA registers used in method
     * @return {@code non-null;} appropriate set
     */
    public static IntSet makeInterferenceSet(int countRegs) {
        return countRegs <= INTERFERENCE_SET_THRESHOLD_SIZE
                ? new BitIntSet(countRegs)
                : new ListIntSet();
    }

    /**
     * Make IntSet for register live in/out sets.
     *
     * @param countRegs {@code >=0;} count of SSA registers used in method
     * @return {@code non-null;} appropriate set
     */
    /*package*/
    static IntSet makeLivenessSet(int countRegs) {
        return countRegs <= LIVENESS_SET_THRESHOLD_SIZE
                ? new BitIntSet(countRegs)
                : new ListIntSet();
    }
}
