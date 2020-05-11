

package com.xiaoyv.dx.dex.file;

import com.xiaoyv.dex.DexFormat;
import com.xiaoyv.dex.DexIndexOverflowException;
import com.xiaoyv.dx.command.dexer.Main;

import java.util.Formatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Member (field or method) refs list section of a {@code .dex} file.
 */
public abstract class MemberIdsSection extends UniformItemSection {

    /**
     * Constructs an instance. The file offset is initially unknown.
     *
     * @param name {@code null-ok;} the name of this instance, for annotation
     *             purposes
     * @param file {@code non-null;} file that this instance is part of
     */
    public MemberIdsSection(String name, DexFile file) {
        super(name, file, 4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void orderItems() {
        int idx = 0;

        if (items().size() > DexFormat.MAX_MEMBER_IDX + 1) {
            throw new DexIndexOverflowException(getTooManyMembersMessage());
        }

        for (Object i : items()) {
            ((MemberIdItem) i).setIndex(idx);
            idx++;
        }
    }

    private String getTooManyMembersMessage() {
        Map<String, AtomicInteger> membersByPackage = new TreeMap<String, AtomicInteger>();
        for (Object member : items()) {
            String packageName = ((MemberIdItem) member).getDefiningClass().getPackageName();
            AtomicInteger count = membersByPackage.get(packageName);
            if (count == null) {
                count = new AtomicInteger();
                membersByPackage.put(packageName, count);
            }
            count.incrementAndGet();
        }

        Formatter formatter = new Formatter();
        try {
            String memberType = this instanceof MethodIdsSection ? "method" : "field";
            formatter.format("Too many %s references: %d; max is %d.%n" +
                            Main.getTooManyIdsErrorMessage() + "%n" +
                            "References by package:",
                    memberType, items().size(), DexFormat.MAX_MEMBER_IDX + 1);
            for (Map.Entry<String, AtomicInteger> entry : membersByPackage.entrySet()) {
                formatter.format("%n%6d %s", entry.getValue().get(), entry.getKey());
            }
            return formatter.toString();
        } finally {
            formatter.close();
        }
    }

}
