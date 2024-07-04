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
    companion object {
        /**
         * Converts a pair to a [NpmDependency].
         */
        fun fromMap(map: Map.Entry<String, String>): NpmDependency {
            return NpmDependency(map.key, map.value)
        }

        /**
         * Converts a map to a set of [NpmDependency].
         */
        fun fromMap(map: Map<String, String>): Set<NpmDependency> {
            return map.entries.map { fromMap(it) }.toSet()
        }
    }
}

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

/**
 * Represents a script in a package.json file.
 * [name] is the name of the script.
 * [command] is the command of the script.
 */
data class NpmScript(
    val name: String,
    val command: String,
) {
    companion object {
        /**
         * Converts a pair to a [NpmScript].
         */
        fun from(p: Pair<String, String>): NpmScript {
            return NpmScript(p.first, p.second)
        }

        /**
         * Converts a map to a set of [NpmScript].
         */
        fun fromMap(map: Map<String, String>): Set<NpmScript> {
            return map.entries.map { from(it.toPair()) }.toSet()
        }
    }
}

/**
 * Represents a package.json file.
 * [name] is the name of the package.
 * [version] is the version of the package.
 * [author] is the author of the package.
 * [description] is the description of the package.
 * [main] is the main file of the package.
 * [license] is the license of the package.
 * [scripts] are the scripts of the package.
 * [dependencies] are the dependencies of the package.
 * [devDependencies] are the devDependencies of the package.
 * [repository] is the repository of the package.
 * [homepage] is the homepage of the package.
 */
data class NpmPackageFile(
    val name: String,
    val version: String = "1.0.0",
    val author: String,
    val description: String,
    val main: String,
    val license: String,
    val scripts: Set<NpmScript>,
    val dependencies: Set<NpmDependency>,
    val devDependencies: Set<NpmDependency>,
    val repository: NpmRepository,
    val homepage: String,
) {
    /**
     * Converts the package file to a map.
     */
    fun toMap(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "version" to version,
            "author" to author,
            "description" to description,
            "main" to main,
            "license" to license,
            "scripts" to scripts.associate { it.name to it.command },
            "dependencies" to dependencies.associate { it.name to it.version },
            "devDependencies" to devDependencies.associate { it.name to it.version },
            "repository" to mapOf("type" to repository.type, "url" to repository.url),
            "homepage" to homepage,
        )
    }
}
