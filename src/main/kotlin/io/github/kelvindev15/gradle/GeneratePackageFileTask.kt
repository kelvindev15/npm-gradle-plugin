package io.github.kelvindev15.gradle

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.uchuhimo.konf.Config
import com.uchuhimo.konf.OptionalItem
import io.github.kelvindev15.npm.NpmPackageFile
import io.github.kelvindev15.npm.PackageJsonSpec
import io.github.kelvindev15.npm.Repository
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property
import java.io.File

/**
 * The task to generate the package.json file.
 */
open class GeneratePackageFileTask : DefaultTask() {

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    /**
     * The object representing the package.json file.
     */
    @Input
    val packageJson: Property<NpmPackageFile> = project.objects.property()

    /**
     * Generates the package.json file.
     */
    @TaskAction
    fun generatePackageFile() {
        val packageJsonOptions = packageJson.get()
        File(project.projectDir, "package.json").also { file ->
            if (file.exists()) {
                val configuration = Config {
                    addSpec(PackageJsonSpec)
                }.from.json.file(file)
                configuration[PackageJsonSpec.name] = packageJsonOptions.name
                configuration[PackageJsonSpec.version] = packageJsonOptions.version
                configuration[PackageJsonSpec.description] = packageJsonOptions.description
                configuration[PackageJsonSpec.author] = packageJsonOptions.author
                configuration[PackageJsonSpec.license] = packageJsonOptions.license
                configuration[PackageJsonSpec.main] = packageJsonOptions.main
                configuration[PackageJsonSpec.homepage] = packageJsonOptions.homepage
                configuration[PackageJsonSpec.type] = packageJsonOptions.type
                fun addToCurrent(key: OptionalItem<Map<String, String>?>, addition: Map<String, String>) {
                    val current = configuration[key]
                    if (current != null) {
                        configuration[key] = current + addition
                    }
                }
                addToCurrent(PackageJsonSpec.scripts, packageJsonOptions.scripts.associate { it.name to it.command })
                addToCurrent(
                    PackageJsonSpec.dependencies,
                    packageJsonOptions.dependencies.associate { it.name to it.version },
                )
                addToCurrent(
                    PackageJsonSpec.devDependencies,
                    packageJsonOptions.devDependencies.associate { it.name to it.version },
                )
                configuration[PackageJsonSpec.repository] = packageJsonOptions.repository?.let {
                    Repository(it.type, it.url)
                }
                listOf(
                    PackageJsonSpec.name,
                    PackageJsonSpec.version,
                    PackageJsonSpec.description,
                    PackageJsonSpec.author,
                    PackageJsonSpec.license,
                    PackageJsonSpec.main,
                    PackageJsonSpec.private,
                    PackageJsonSpec.scripts,
                    PackageJsonSpec.dependencies,
                    PackageJsonSpec.devDependencies,
                    PackageJsonSpec.homepage,
                    PackageJsonSpec.type,
                    PackageJsonSpec.keywords,
                    PackageJsonSpec.repository,
                    PackageJsonSpec.bugs,
                ).forEach { if (configuration[it] == null) configuration.unset(it) }
                file.writeText("${gson.toJson(configuration.toMap())}\n", Charsets.UTF_8)
            } else {
                file.createNewFile()
                file.writeText("${gson.toJson(packageJson.get().toMap())}\n", Charsets.UTF_8)
            }
        }
    }
}
