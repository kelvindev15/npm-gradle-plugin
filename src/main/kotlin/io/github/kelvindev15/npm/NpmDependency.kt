package io.github.kelvindev15.npm

/**
 * Represents a dependency in a package.json file.
 * [name] is the name of the dependency.
 * [version] is the version of the dependency.
 */
data class NpmDependency(
    val name: String,
    val version: String,
) {
    /**
     * Converts the dependency to a map.
     */
    companion object {
        /**
         * Converts a pair to a [NpmDependency].
         */
        fun from(p: Pair<String, String>): NpmDependency = NpmDependency(p.first, p.second)

        /**
         * Converts a map to a set of [NpmDependency].
         */
        fun from(vararg deps: Pair<String, String>): Set<NpmDependency> = deps.map { from(it) }.toSet()
    }
}
