

package com.xiaoyv.dx.util;

/**
 * Interface for a binary output destination that may be augmented
 * with textual annotations.
 */
public interface AnnotatedOutput
        extends Output {
    /**
     * Get whether this instance will actually keep annotations.
     *
     * @return {@code true} iff annotations are being kept
     */
    boolean annotates();

    /**
     * Get whether this instance is intended to keep verbose annotations.
     * Annotators may use the result of calling this method to inform their
     * annotation activity.
     *
     * @return {@code true} iff annotations are to be verbose
     */
    boolean isVerbose();

    /**
     * Add an annotation for the subsequent output. Any previously
     * open annotation will be closed by this call, and the new
     * annotation marks all subsequent output until another annotation
     * call.
     *
     * @param msg {@code non-null;} the annotation message
     */
    void annotate(String msg);

    /**
     * Add an annotation for a specified amount of subsequent
     * output. Any previously open annotation will be closed by this
     * call. If there is already pending annotation from one or more
     * previous calls to this method, the new call "consumes" output
     * after all the output covered by the previous calls.
     *
     * @param amt {@code >= 0;} the amount of output for this annotation to
     *            cover
     * @param msg {@code non-null;} the annotation message
     */
    void annotate(int amt, String msg);

    /**
     * End the most recent annotation. Subsequent output will be unannotated,
     * until the next call to {@link #annotate}.
     */
    void endAnnotation();

    /**
     * Get the maximum width of the annotated output. This is advisory:
     * Implementations of this interface are encouraged to deal with too-wide
     * output, but annotaters are encouraged to attempt to avoid exceeding
     * the indicated width.
     *
     * @return {@code >= 1;} the maximum width
     */
    int getAnnotationWidth();
}
