package io.github.kelvindev15.npm

import com.uchuhimo.konf.ConfigSpec

/**
 * The specification of the package.json file.
 *
 * [name] The name of the package.
 * [version] The version of the package.
 * [description] The description of the package.
 * [keywords] The keywords of the package.
 * [homepage] The homepage of the package.
 * [bugs] The link where to report bugs from the package.
 * [license] The license of the package.
 * [author] The author of the package.
 * [main] The main file of the package.
 * [repository] The repository of the package.
 * [scripts] The scripts of the package.
 * [dependencies] The dependencies of the package.
 * [devDependencies] The dev dependencies of the package.
 * [private] Whether the package is private.
 * [type] The type of the package.
 */
object PackageJsonSpec : ConfigSpec("") {
    val name by optional<String?>(null)
    val version by optional<String?>(null)
    val description by optional<String?>(null)
    val keywords by optional<List<String>?>(emptyList())
    val homepage by optional<String?>(null)
    val bugs by optional<Bugs?>(null)
    val license by optional<String?>(null)
    val author by optional<String?>(null)
    val main by optional<String?>(null)
    val repository by optional<Repository?>(null)
    val scripts by optional<Map<String, String>?>(null)
    val dependencies by optional<Map<String, String>?>(null)
    val devDependencies by optional<Map<String, String>?>(null)
    val private by optional<Boolean?>(null)
    val type by optional<String?>("module")
}

/**
 * The specification of the package.json's bugs option.
 *
 * [url] The link where to report bugs from the package.
 */
data class Bugs(
    val url: String,
)

/**
 * The specification of the package.json's repository option.
 *
 * [type] The type of the repository.
 * [url] The URL of the repository.
 */
data class Repository(
    val type: String,
    val url: String,
)
