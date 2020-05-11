

package com.xiaoyv.dx.command.grep;

import com.xiaoyv.dex.Dex;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

public final class Main {
    public static void main(String[] args) throws IOException {
        String dexFile = args[0];
        String pattern = args[1];

        Dex dex = new Dex(new File(dexFile));
        int count = new Grep(dex, Pattern.compile(pattern), new PrintWriter(System.out)).grep();
        int status = (count > 0) ? 0 : 1;
        System.err.println("exit code " + status);
//        System.exit(status);
    }
}
