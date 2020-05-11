

package com.xiaoyv.dx.cf.iface;

import com.xiaoyv.dx.rop.cst.ConstantPool;
import com.xiaoyv.dx.rop.cst.CstString;
import com.xiaoyv.dx.rop.cst.CstType;
import com.xiaoyv.dx.rop.type.TypeList;

/**
 * Interface for things which purport to be class files or reasonable
 * facsimiles thereof.
 *
 * <p><b>Note:</b> The fields referred to in this documentation are of the
 * {@code ClassFile} structure defined in vmspec-2 sec4.1.
 */
public interface ClassFile extends HasAttribute {
    /**
     * Gets the field {@code magic}.
     *
     * @return the value in question
     */
    public int getMagic();

    /**
     * Gets the field {@code minor_version}.
     *
     * @return the value in question
     */
    public int getMinorVersion();

    /**
     * Gets the field {@code major_version}.
     *
     * @return the value in question
     */
    public int getMajorVersion();

    /**
     * Gets the field {@code access_flags}.
     *
     * @return the value in question
     */
    public int getAccessFlags();

    /**
     * Gets the field {@code this_class}, interpreted as a type constant.
     *
     * @return {@code non-null;} the value in question
     */
    public CstType getThisClass();

    /**
     * Gets the field {@code super_class}, interpreted as a type constant
     * if non-zero.
     *
     * @return {@code null-ok;} the value in question
     */
    public CstType getSuperclass();

    /**
     * Gets the field {@code constant_pool} (along with
     * {@code constant_pool_count}).
     *
     * @return {@code non-null;} the constant pool
     */
    public ConstantPool getConstantPool();

    /**
     * Gets the field {@code interfaces} (along with
     * {@code interfaces_count}).
     *
     * @return {@code non-null;} the list of interfaces
     */
    public TypeList getInterfaces();

    /**
     * Gets the field {@code fields} (along with
     * {@code fields_count}).
     *
     * @return {@code non-null;} the list of fields
     */
    public FieldList getFields();

    /**
     * Gets the field {@code methods} (along with
     * {@code methods_count}).
     *
     * @return {@code non-null;} the list of fields
     */
    public MethodList getMethods();

    /**
     * Gets the field {@code attributes} (along with
     * {@code attributes_count}).
     *
     * @return {@code non-null;} the list of attributes
     */
    public AttributeList getAttributes();

    /**
     * Gets the name out of the {@code SourceFile} attribute of this
     * file, if any. This is a convenient shorthand for scrounging around
     * the class's attributes.
     *
     * @return {@code non-null;} the constant pool
     */
    public CstString getSourceFile();
}
