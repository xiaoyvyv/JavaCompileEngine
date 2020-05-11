

package com.xiaoyv.dx.rop.annotation;

import com.xiaoyv.dx.util.ToHuman;

/**
 * Visibility scope of an annotation.
 */
public enum AnnotationVisibility implements ToHuman {
    RUNTIME("runtime"),
    BUILD("build"),
    SYSTEM("system"),
    EMBEDDED("embedded");

    /**
     * {@code non-null;} the human-oriented string representation
     */
    private final String human;

    /**
     * Constructs an instance.
     *
     * @param human {@code non-null;} the human-oriented string representation
     */
    AnnotationVisibility(String human) {
        this.human = human;
    }

    /**
     * {@inheritDoc}
     */
    public String toHuman() {
        return human;
    }
}
