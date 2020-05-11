

package com.xiaoyv.dx.ssa;

import com.xiaoyv.dx.rop.code.RegisterSpec;
import com.xiaoyv.dx.util.IntList;

/**
 * This class maps one register space into another, with
 * each mapping built up individually and added via addMapping()
 */
public class BasicRegisterMapper extends RegisterMapper {
    /**
     * indexed by old register, containing new name
     */
    private IntList oldToNew;

    /**
     * running count of used registers in new namespace
     */
    private int runningCountNewRegisters;

    /**
     * Creates a new OneToOneRegisterMapper.
     *
     * @param countOldRegisters the number of registers in the old name space
     */
    public BasicRegisterMapper(int countOldRegisters) {
        oldToNew = new IntList(countOldRegisters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNewRegisterCount() {
        return runningCountNewRegisters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegisterSpec map(RegisterSpec registerSpec) {
        if (registerSpec == null) {
            return null;
        }

        int newReg;
        try {
            newReg = oldToNew.get(registerSpec.getReg());
        } catch (IndexOutOfBoundsException ex) {
            newReg = -1;
        }

        if (newReg < 0) {
            throw new RuntimeException("no mapping specified for register");
        }

        return registerSpec.withReg(newReg);
    }

    /**
     * Returns the new-namespace mapping for the specified
     * old-namespace register, or -1 if one exists.
     *
     * @param oldReg {@code >= 0;} old-namespace register
     * @return new-namespace register or -1 if none
     */
    public int oldToNew(int oldReg) {
        if (oldReg >= oldToNew.size()) {
            return -1;
        }

        return oldToNew.get(oldReg);
    }

    /**
     * {@inheritDoc}
     */
    public String toHuman() {
        StringBuilder sb = new StringBuilder();

        sb.append("Old\tNew\n");
        int sz = oldToNew.size();

        for (int i = 0; i < sz; i++) {
            sb.append(i);
            sb.append('\t');
            sb.append(oldToNew.get(i));
            sb.append('\n');
        }

        sb.append("new reg count:");

        sb.append(runningCountNewRegisters);
        sb.append('\n');

        return sb.toString();
    }

    /**
     * Adds a mapping to the mapper. If oldReg has already been mapped,
     * overwrites previous mapping with new mapping.
     *
     * @param oldReg   {@code >= 0;} old register
     * @param newReg   {@code >= 0;} new register
     * @param category {@code 1..2;} width of reg
     */
    public void addMapping(int oldReg, int newReg, int category) {
        if (oldReg >= oldToNew.size()) {
            // expand the array as necessary
            for (int i = oldReg - oldToNew.size(); i >= 0; i--) {
                oldToNew.add(-1);
            }
        }

        oldToNew.set(oldReg, newReg);

        if (runningCountNewRegisters < (newReg + category)) {
            runningCountNewRegisters = newReg + category;
        }
    }
}
