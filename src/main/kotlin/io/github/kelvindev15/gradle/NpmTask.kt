package io.github.kelvindev15.gradle

import io.github.kelvindev15.utils.NpmExec
import io.github.kelvindev15.utils.Platform
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.TaskAction

/**
 * The task to run a npm command.
 */
open class NpmTask : Exec() {
    private var npmArguments = emptyList<String>()

    /**
     * The arguments to pass to the npm command.
     */
    fun arguments(vararg args: String) {
        npmArguments = args.toList()
    }

    @TaskAction
    override fun exec() {
        when (Platform.detectOS()) {
            Platform.OS.WINDOWS -> commandLine("cmd", "/c", NpmExec.bin, *npmArguments.toTypedArray())
            else -> commandLine(NpmExec.bin, *npmArguments.toTypedArray())
        }
        super.exec()
    }
}
