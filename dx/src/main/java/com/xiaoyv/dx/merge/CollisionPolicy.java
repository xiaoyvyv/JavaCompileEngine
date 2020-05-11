

package com.xiaoyv.dx.merge;

/**
 * What to do when two dex files define the same class.
 */
public enum CollisionPolicy {

    /**
     * Keep the class def from the first dex file and discard the def from the
     * second dex file. This policy is appropriate for incremental builds.
     */
    KEEP_FIRST,

    /**
     * Forbid collisions. This policy is appropriate for merging libraries.
     */
    FAIL
}
