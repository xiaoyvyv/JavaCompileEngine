/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package sun.reflect;

import java.lang.reflect.Field;

/**
 * An interface which gives privileged packages Java-level access to
 * internals of java.lang.reflect.
 */

public interface LangReflectAccess {
    /**
     * Creates a new Field. Access checks as per
     * AccessibleObject are not overridden.
     */
    public java.lang.reflect.Field newField(Class<?> declaringClass,
                                            String name,
                                            Class<?> type,
                                            int modifiers,
                                            int slot,
                                            String signature,
                                            byte[] annotations);

    /**
     * Creates a new Method. Access checks as per
     * AccessibleObject are not overridden.
     */
    public java.lang.reflect.Method newMethod(Class<?> declaringClass,
                                              String name,
                                              Class<?>[] parameterTypes,
                                              Class<?> returnType,
                                              Class<?>[] checkedExceptions,
                                              int modifiers,
                                              int slot,
                                              String signature,
                                              byte[] annotations,
                                              byte[] parameterAnnotations,
                                              byte[] annotationDefault);

    /**
     * Creates a new Constructor. Access checks as
     * per AccessibleObject are not overridden.
     */
    public <T> java.lang.reflect.Constructor<T> newConstructor(Class<T> declaringClass,
                                                               Class<?>[] parameterTypes,
                                                               Class<?>[] checkedExceptions,
                                                               int modifiers,
                                                               int slot,
                                                               String signature,
                                                               byte[] annotations,
                                                               byte[] parameterAnnotations);

    /**
     * Gets the MethodAccessor object for a Method
     */
    public MethodAccessor getMethodAccessor(java.lang.reflect.Method m);

    /**
     * Sets the MethodAccessor object for a Method
     */
    public void setMethodAccessor(java.lang.reflect.Method m, MethodAccessor accessor);

    /**
     * Gets the ConstructorAccessor object for a
     * Constructor
     */
    public ConstructorAccessor getConstructorAccessor(java.lang.reflect.Constructor<?> c);

    /**
     * Sets the ConstructorAccessor object for a
     * Constructor
     */
    public void setConstructorAccessor(java.lang.reflect.Constructor<?> c,
                                       ConstructorAccessor accessor);

    /**
     * Gets the byte[] that encodes TypeAnnotations on an Executable.
     */
    public byte[] getExecutableTypeAnnotationBytes(java.lang.reflect.Executable ex);

    /**
     * Gets the "slot" field from a Constructor (used for serialization)
     */
    public int getConstructorSlot(java.lang.reflect.Constructor<?> c);

    /**
     * Gets the "signature" field from a Constructor (used for serialization)
     */
    public String getConstructorSignature(java.lang.reflect.Constructor<?> c);

    /**
     * Gets the "annotations" field from a Constructor (used for serialization)
     */
    public byte[] getConstructorAnnotations(java.lang.reflect.Constructor<?> c);

    /**
     * Gets the "parameterAnnotations" field from a Constructor (used for serialization)
     */
    public byte[] getConstructorParameterAnnotations(java.lang.reflect.Constructor<?> c);

    //
    // Copying routines, needed to quickly fabricate new Field,
    // Method, and Constructor objects from templates
    //

    /**
     * Makes a "child" copy of a Method
     */
    public java.lang.reflect.Method copyMethod(java.lang.reflect.Method arg);

    /**
     * Makes a "child" copy of a Field
     */
    public java.lang.reflect.Field copyField(Field arg);

    /**
     * Makes a "child" copy of a Constructor
     */
    public <T> java.lang.reflect.Constructor<T> copyConstructor(java.lang.reflect.Constructor<T> arg);
}
