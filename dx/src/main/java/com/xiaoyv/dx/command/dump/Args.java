

package com.xiaoyv.dx.command.dump;

/**
 * contains command line parsedArgs values
 */
class Args {
    /**
     * whether to run in debug mode
     */
    boolean debug = false;

    /**
     * whether to dump raw bytes where salient
     */
    boolean rawBytes = false;

    /**
     * whether to dump information about basic blocks
     */
    boolean basicBlocks = false;

    /**
     * whether to dump regiserized blocks
     */
    boolean ropBlocks = false;

    /**
     * whether to dump SSA-form blocks
     */
    boolean ssaBlocks = false;

    /**
     * Step in SSA processing to stop at, or null for all
     */
    String ssaStep = null;

    /**
     * whether to run SSA optimizations
     */
    boolean optimize = false;

    /**
     * whether to be strict about parsing classfiles
     */
    boolean strictParse = false;

    /**
     * max width for columnar output
     */
    int width = 0;

    /**
     * whether to dump flow-graph in "dot" format
     */
    boolean dotDump = false;

    /**
     * if non-null, an explicit method to dump
     */
    String method;

}
