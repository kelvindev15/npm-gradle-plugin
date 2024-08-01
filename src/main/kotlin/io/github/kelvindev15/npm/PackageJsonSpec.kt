package io.github.kelvindev15.npm

import com.uchuhimo.konf.ConfigSpec

/**
 * The specification of the package.json file.
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
 */
data class Bugs(
    val url: String,
)

/**
 * The specification of the package.json's repository option.
 */
data class Repository(
    val type: String,
    val url: String,
)
