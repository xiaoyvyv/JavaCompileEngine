package com.xiaoyv.java.compiler

import java.io.File
import java.io.PrintWriter

/**
 * JavaPrintWriter
 *
 * @author why
 * @since 2022/3/7
 */
class JavaPrintWriter(file: File) : PrintWriter(file) {

    constructor(fileName: String) : this(File(fileName))
}