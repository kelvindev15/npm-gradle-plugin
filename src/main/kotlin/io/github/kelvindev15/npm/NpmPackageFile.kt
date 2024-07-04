package io.github.kelvindev15.npm

enum class DependencyVersion

data class NpmDependency(
    val name: String,
    val version: String,
) {
    companion object {
        fun fromMap(map: Map.Entry<String, String>): NpmDependency {
            return NpmDependency(map.key, map.value)
        }

        fun fromMap(map: Map<String, String>): Set<NpmDependency> {
            return map.entries.map { fromMap(it) }.toSet()
        }
    }
}

data class NpmRepository(
    val type: String,
    val url: String,
) {
    companion object {
        fun from(p: Pair<String, String>): NpmRepository {
            return NpmRepository(p.first, p.second)
        }
    }
}

data class NpmScript(
    val name: String,
    val command: String,
) {
    companion object {
        fun from(p: Pair<String, String>): NpmScript {
            return NpmScript(p.first, p.second)
        }

        fun fromMap(map: Map<String, String>): Set<NpmScript> {
            return map.entries.map { from(it.toPair()) }.toSet()
        }
    }
}

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
