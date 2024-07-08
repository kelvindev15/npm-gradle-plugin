package io.github.kelvindev15.utils

/**
 * Represents the npm executable.
 */
object NpmExec {
    /**
     * The path to the npm executable.
     */
    val bin: String = when (Platform.detectOS()) {
        Platform.OS.WINDOWS -> CLI.runCommand("where npm.exe").trim()
        else -> CLI.runCommand("which npm").trim()
    }
}
