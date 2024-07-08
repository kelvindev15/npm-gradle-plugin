package io.github.kelvindev15.utils

import java.io.File

/**
 * Represents a command line interface.
 */
object CLI {
    /**
     * Runs a command and returns the output.
     */
    fun runCommand(command: String): String {
        val tmpFile = File.createTempFile("npm-plugin", "txt")
        val process = ProcessBuilder(command.split(" "))
            .redirectOutput(tmpFile)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
        process.waitFor()
        return tmpFile.readText()
    }
}
