package io.github.kelvindev15.npm

/**
 * Represents a repository in a package.json file.
 * [type] is the type of the repository.
 * [url] is the URL of the repository.
 */
data class NpmRepository(
    val type: String,
    val url: String,
) {
    companion object {
        /**
         * Converts a pair to a [NpmRepository].
         */
        fun from(p: Pair<String, String>): NpmRepository {
            return NpmRepository(p.first, p.second)
        }
    }
}
