package io.github.kelvindev15.npm

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
