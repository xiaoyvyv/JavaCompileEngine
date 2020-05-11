

package com.xiaoyv.dx.command.dump;

import com.xiaoyv.dx.cf.code.ConcreteMethod;
import com.xiaoyv.dx.cf.code.Ropper;
import com.xiaoyv.dx.cf.direct.DirectClassFile;
import com.xiaoyv.dx.cf.direct.StdAttributeFactory;
import com.xiaoyv.dx.cf.iface.Member;
import com.xiaoyv.dx.cf.iface.Method;
import com.xiaoyv.dx.cf.iface.ParseObserver;
import com.xiaoyv.dx.rop.code.AccessFlags;
import com.xiaoyv.dx.rop.code.BasicBlock;
import com.xiaoyv.dx.rop.code.BasicBlockList;
import com.xiaoyv.dx.rop.code.DexTranslationAdvice;
import com.xiaoyv.dx.rop.code.RopMethod;
import com.xiaoyv.dx.rop.code.TranslationAdvice;
import com.xiaoyv.dx.ssa.Optimizer;
import com.xiaoyv.dx.util.ByteArray;
import com.xiaoyv.dx.util.Hex;
import com.xiaoyv.dx.util.IntList;

/**
 * Dumps the pred/succ graph of methods into a format compatible
 * with the popular graph utility "dot".
 */
public class DotDumper implements ParseObserver {
    private DirectClassFile classFile;

    private final byte[] bytes;
    private final String filePath;
    private final boolean strictParse;
    private final boolean optimize;
    private final Args args;

    static void dump(byte[] bytes, String filePath, Args args) {
        new DotDumper(bytes, filePath, args).run();
    }

    DotDumper(byte[] bytes, String filePath, Args args) {
        this.bytes = bytes;
        this.filePath = filePath;
        this.strictParse = args.strictParse;
        this.optimize = args.optimize;
        this.args = args;
    }

    private void run() {
        ByteArray ba = new ByteArray(bytes);

        /*
         * First, parse the file completely, so we can safely refer to
         * attributes, etc.
         */
        classFile = new DirectClassFile(ba, filePath, strictParse);
        classFile.setAttributeFactory(StdAttributeFactory.THE_ONE);
        classFile.getMagic(); // Force parsing to happen.

        // Next, reparse it and observe the process.
        DirectClassFile liveCf =
                new DirectClassFile(ba, filePath, strictParse);
        liveCf.setAttributeFactory(StdAttributeFactory.THE_ONE);
        liveCf.setObserver(this);
        liveCf.getMagic(); // Force parsing to happen.
    }

    /**
     * @param name method name
     * @return true if this method should be dumped
     */
    protected boolean shouldDumpMethod(String name) {
        return args.method == null || args.method.equals(name);
    }

    public void changeIndent(int indentDelta) {
        // This space intentionally left blank.
    }

    public void parsed(ByteArray bytes, int offset, int len, String human) {
        // This space intentionally left blank.
    }

    /**
     * {@inheritDoc}
     */
    public void startParsingMember(ByteArray bytes, int offset, String name,
                                   String descriptor) {
        // This space intentionally left blank.
    }

    public void endParsingMember(ByteArray bytes, int offset, String name,
                                 String descriptor, Member member) {
        if (!(member instanceof Method)) {
            return;
        }

        if (!shouldDumpMethod(name)) {
            return;
        }

        ConcreteMethod meth = new ConcreteMethod((Method) member, classFile,
                true, true);

        TranslationAdvice advice = DexTranslationAdvice.THE_ONE;
        RopMethod rmeth =
                Ropper.convert(meth, advice, classFile.getMethods());

        if (optimize) {
            boolean isStatic = AccessFlags.isStatic(meth.getAccessFlags());
            rmeth = Optimizer.optimize(rmeth,
                    BaseDumper.computeParamWidth(meth, isStatic), isStatic,
                    true, advice);
        }

        System.out.println("digraph " + name + "{");

        System.out.println("\tfirst -> n"
                + Hex.u2(rmeth.getFirstLabel()) + ";");

        BasicBlockList blocks = rmeth.getBlocks();

        int sz = blocks.size();
        for (int i = 0; i < sz; i++) {
            BasicBlock bb = blocks.get(i);
            int label = bb.getLabel();
            IntList successors = bb.getSuccessors();

            if (successors.size() == 0) {
                System.out.println("\tn" + Hex.u2(label) + " -> returns;");
            } else if (successors.size() == 1) {
                System.out.println("\tn" + Hex.u2(label) + " -> n"
                        + Hex.u2(successors.get(0)) + ";");
            } else {
                System.out.print("\tn" + Hex.u2(label) + " -> {");
                for (int j = 0; j < successors.size(); j++) {
                    int successor = successors.get(j);

                    if (successor != bb.getPrimarySuccessor()) {
                        System.out.print(" n" + Hex.u2(successor) + " ");
                    }

                }
                System.out.println("};");

                System.out.println("\tn" + Hex.u2(label) + " -> n"
                        + Hex.u2(bb.getPrimarySuccessor())
                        + " [label=\"primary\"];");


            }
        }

        System.out.println("}");
    }
}
