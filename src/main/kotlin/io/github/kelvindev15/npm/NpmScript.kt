package io.github.kelvindev15.npm

/**
 * Represents a script in a package.json file.
 * [name] is the name of the script.
 * [command] is the command of the script.
 */
data class NpmScript(
    val name: String,
    val command: String,
) {
    /**
     * Converts the script to a map.
     */
    companion object {
        /**
         * Converts a pair to a [NpmScript].
         */
        fun from(p: Pair<String, String>): NpmScript = NpmScript(p.first, p.second)

        /**
         * Converts a map to a set of [NpmScript].
         */
        fun from(vararg deps: Pair<String, String>): Set<NpmScript> = deps.map { from(it) }.toSet()
    }
}
