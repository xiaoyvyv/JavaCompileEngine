

package com.xiaoyv.dx.ssa.back;

import com.xiaoyv.dx.ssa.BasicRegisterMapper;
import com.xiaoyv.dx.ssa.RegisterMapper;
import com.xiaoyv.dx.ssa.SsaMethod;

/**
 * A register allocator that maps SSA register n to Rop register 2*n,
 * essentially preserving the original mapping and remaining agnostic
 * about normal or wide categories. Used for debugging.
 */
public class NullRegisterAllocator extends RegisterAllocator {
    /**
     * {@inheritDoc}
     */
    public NullRegisterAllocator(SsaMethod ssaMeth,
                                 InterferenceGraph interference) {
        super(ssaMeth, interference);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean wantsParamsMovedHigh() {
        // We're not smart enough for this.
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegisterMapper allocateRegisters() {
        int oldRegCount = ssaMeth.getRegCount();

        BasicRegisterMapper mapper = new BasicRegisterMapper(oldRegCount);

        for (int i = 0; i < oldRegCount; i++) {
            mapper.addMapping(i, i * 2, 2);
        }

        return mapper;
    }
}
