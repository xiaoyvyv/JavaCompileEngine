

package com.xiaoyv.dx.dex.file;

import com.xiaoyv.dx.rop.cst.CstString;
import com.xiaoyv.dx.util.AnnotatedOutput;
import com.xiaoyv.dx.util.ToHuman;

import java.io.PrintWriter;

/**
 * Representation of a member (field or method) of a class, for the
 * purposes of encoding it inside a {@link ClassDataItem}.
 */
public abstract class EncodedMember implements ToHuman {
    /**
     * access flags
     */
    private final int accessFlags;

    /**
     * Constructs an instance.
     *
     * @param accessFlags access flags for the member
     */
    public EncodedMember(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    /**
     * Gets the access flags.
     *
     * @return the access flags
     */
    public final int getAccessFlags() {
        return accessFlags;
    }

    /**
     * Gets the name.
     *
     * @return {@code non-null;} the name
     */
    public abstract CstString getName();

    /**
     * Does a human-friendly dump of this instance.
     *
     * @param out     {@code non-null;} where to dump
     * @param verbose whether to be verbose with the output
     */
    public abstract void debugPrint(PrintWriter out, boolean verbose);

    /**
     * Populates a {@link DexFile} with items from within this instance.
     *
     * @param file {@code non-null;} the file to populate
     */
    public abstract void addContents(DexFile file);

    /**
     * Encodes this instance to the given output.
     *
     * @param file      {@code non-null;} file this instance is part of
     * @param out       {@code non-null;} where to write to
     * @param lastIndex {@code >= 0;} the previous member index value encoded, or
     *                  {@code 0} if this is the first element to encode
     * @param dumpSeq   {@code >= 0;} sequence number of this instance for
     *                  annotation purposes
     * @return {@code >= 0;} the member index value that was encoded
     */
    public abstract int encode(DexFile file, AnnotatedOutput out,
                               int lastIndex, int dumpSeq);
}
