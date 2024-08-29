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
        val interpreter = when (Platform.detectOS()) {
            Platform.OS.WINDOWS -> listOf("cmd.exe", "/c")
            else -> emptyList()
        }
        val tmpFile = File.createTempFile("npm-plugin", "txt")
        val errorFile = File.createTempFile("npm-plugin", "txt")
        val process = ProcessBuilder(interpreter + command.split(" "))
            .redirectOutput(tmpFile)
            .redirectError(errorFile)
            .start()
        process.waitFor()
        return tmpFile.readLines().first()
    }
}
