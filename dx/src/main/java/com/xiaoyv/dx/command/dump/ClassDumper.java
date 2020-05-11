

package com.xiaoyv.dx.command.dump;

import com.xiaoyv.dx.cf.direct.DirectClassFile;
import com.xiaoyv.dx.cf.direct.StdAttributeFactory;
import com.xiaoyv.dx.util.ByteArray;

import java.io.PrintStream;

/**
 * Utility to dump the contents of class files in a human-friendly form.
 */
public final class ClassDumper
        extends BaseDumper {
    /**
     * Dumps the given array, interpreting it as a class file.
     *
     * @param bytes    {@code non-null;} bytes of the (alleged) class file
     * @param out      {@code non-null;} where to dump to
     *                 passed in as &lt;= 0
     * @param filePath the file path for the class, excluding any base
     *                 directory specification
     * @param args     bag of commandline arguments
     */
    public static void dump(byte[] bytes, PrintStream out,
                            String filePath, Args args) {
        ClassDumper cd =
                new ClassDumper(bytes, out, filePath, args);
        cd.dump();
    }

    /**
     * Constructs an instance. This class is not publicly instantiable.
     * Use {@link #dump}.
     */
    private ClassDumper(byte[] bytes, PrintStream out,
                        String filePath, Args args) {
        super(bytes, out, filePath, args);
    }

    /**
     * Does the dumping.
     */
    public void dump() {
        byte[] bytes = getBytes();
        ByteArray ba = new ByteArray(bytes);
        DirectClassFile cf =
                new DirectClassFile(ba, getFilePath(), getStrictParse());

        cf.setAttributeFactory(StdAttributeFactory.THE_ONE);
        cf.setObserver(this);
        cf.getMagic(); // Force parsing to happen.

        int at = getAt();
        if (at != bytes.length) {
            parsed(ba, at, bytes.length - at, "<extra data at end of file>");
        }
    }
}
