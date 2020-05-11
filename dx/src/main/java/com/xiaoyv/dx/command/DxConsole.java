

package com.xiaoyv.dx.command;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Provides standard and error PrintStream object to output information.<br>
 * By default the PrintStream objects link to {@code System.out} and
 * {@code System.err} but they can be changed to link to other
 * PrintStream.
 */
public class DxConsole {
    /**
     * Standard output stream. Links to {@code System.out} by default.
     */
    public static PrintStream out = System.out;

    /**
     * Error output stream. Links to {@code System.err} by default.
     */
    public static PrintStream err = System.err;

    /**
     * Output stream which prints to nowhere.
     */
    public static final PrintStream noop = new PrintStream(new OutputStream() {

        @Override
        public void write(int b) throws IOException {
            // noop
        }
    });
}
