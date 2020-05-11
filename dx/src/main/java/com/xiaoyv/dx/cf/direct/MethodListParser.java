

package com.xiaoyv.dx.cf.direct;

import com.xiaoyv.dx.cf.iface.AttributeList;
import com.xiaoyv.dx.cf.iface.Member;
import com.xiaoyv.dx.cf.iface.StdMethod;
import com.xiaoyv.dx.cf.iface.StdMethodList;
import com.xiaoyv.dx.rop.code.AccessFlags;
import com.xiaoyv.dx.rop.cst.CstNat;
import com.xiaoyv.dx.rop.cst.CstType;

/**
 * Parser for lists of methods in a class file.
 */
final /*package*/ class MethodListParser extends MemberListParser {
    /**
     * {@code non-null;} list in progress
     */
    final private StdMethodList methods;

    /**
     * Constructs an instance.
     *
     * @param cf               {@code non-null;} the class file to parse from
     * @param definer          {@code non-null;} class being defined
     * @param offset           offset in {@code bytes} to the start of the list
     * @param attributeFactory {@code non-null;} attribute factory to use
     */
    public MethodListParser(DirectClassFile cf, CstType definer,
                            int offset, AttributeFactory attributeFactory) {
        super(cf, definer, offset, attributeFactory);
        methods = new StdMethodList(getCount());
    }

    /**
     * Gets the parsed list.
     *
     * @return {@code non-null;} the parsed list
     */
    public StdMethodList getList() {
        parseIfNecessary();
        return methods;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String humanName() {
        return "method";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String humanAccessFlags(int accessFlags) {
        return AccessFlags.methodString(accessFlags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getAttributeContext() {
        return AttributeFactory.CTX_METHOD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Member set(int n, int accessFlags, CstNat nat,
                         AttributeList attributes) {
        StdMethod meth =
                new StdMethod(getDefiner(), accessFlags, nat, attributes);

        methods.set(n, meth);
        return meth;
    }
}
