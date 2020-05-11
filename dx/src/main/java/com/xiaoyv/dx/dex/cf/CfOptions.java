

package com.xiaoyv.dx.dex.cf;

import com.xiaoyv.dx.dex.code.PositionList;

import java.io.PrintStream;

/**
 * A class to contain options passed into dex.cf
 */
public class CfOptions {
    /**
     * how much source position info to preserve
     */
    public int positionInfo = PositionList.LINES;

    /**
     * whether to keep local variable information
     */
    public boolean localInfo = false;

    /**
     * whether strict file-name-vs-class-name checking should be done
     */
    public boolean strictNameCheck = true;

    /**
     * whether to do SSA/register optimization
     */
    public boolean optimize = false;

    /**
     * filename containing list of methods to optimize
     */
    public String optimizeListFile = null;

    /**
     * filename containing list of methods <i>not</i> to optimize
     */
    public String dontOptimizeListFile = null;

    /**
     * whether to print statistics to stdout at end of compile cycle
     */
    public boolean statistics;

    /**
     * where to issue warnings to
     */
    public PrintStream warn = System.err;
}
