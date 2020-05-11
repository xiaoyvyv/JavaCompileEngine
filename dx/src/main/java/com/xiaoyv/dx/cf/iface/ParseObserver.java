

package com.xiaoyv.dx.cf.iface;

import com.xiaoyv.dx.util.ByteArray;

/**
 * Observer of parsing in action. This is used to supply feedback from
 * the various things that parse particularly to the dumping utilities.
 */
public interface ParseObserver {
    /**
     * Indicate that the level of indentation for a dump should increase
     * or decrease (positive or negative argument, respectively).
     *
     * @param indentDelta the amount to change indentation
     */
    public void changeIndent(int indentDelta);

    /**
     * Indicate that a particular member is now being parsed.
     *
     * @param bytes      {@code non-null;} the source that is being parsed
     * @param offset     offset into {@code bytes} for the start of the
     *                   member
     * @param name       {@code non-null;} name of the member
     * @param descriptor {@code non-null;} descriptor of the member
     */
    public void startParsingMember(ByteArray bytes, int offset, String name,
                                   String descriptor);

    /**
     * Indicate that a particular member is no longer being parsed.
     *
     * @param bytes      {@code non-null;} the source that was parsed
     * @param offset     offset into {@code bytes} for the end of the
     *                   member
     * @param name       {@code non-null;} name of the member
     * @param descriptor {@code non-null;} descriptor of the member
     * @param member     {@code non-null;} the actual member that was parsed
     */
    public void endParsingMember(ByteArray bytes, int offset, String name,
                                 String descriptor, Member member);

    /**
     * Indicate that some parsing happened.
     *
     * @param bytes  {@code non-null;} the source that was parsed
     * @param offset offset into {@code bytes} for what was parsed
     * @param len    number of bytes parsed
     * @param human  {@code non-null;} human form for what was parsed
     */
    public void parsed(ByteArray bytes, int offset, int len, String human);
}
