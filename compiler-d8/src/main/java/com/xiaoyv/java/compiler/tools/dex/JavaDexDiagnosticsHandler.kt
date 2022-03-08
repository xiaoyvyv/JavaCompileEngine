package com.xiaoyv.java.compiler.tools.dex

import com.android.tools.r8.Diagnostic
import com.android.tools.r8.DiagnosticsHandler
import com.android.tools.r8.DiagnosticsLevel
import com.xiaoyv.java.compiler.JavaEngine

/**
 * JavaDexDiagnosticsHandler
 *
 * @author why
 * @since 2022/3/8
 */
class JavaDexDiagnosticsHandler : DiagnosticsHandler {

    override fun modifyDiagnosticsLevel(
        level: DiagnosticsLevel,
        diagnostic: Diagnostic
    ): DiagnosticsLevel {
        return DiagnosticsLevel.valueOf(JavaEngine.compilerSetting.dexLogLevel)
    }
}