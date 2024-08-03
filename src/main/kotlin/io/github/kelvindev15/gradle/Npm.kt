package io.github.kelvindev15.gradle

import io.github.kelvindev15.npm.NpmDependency
import io.github.kelvindev15.npm.NpmPackageFile
import io.github.kelvindev15.npm.NpmRepository
import io.github.kelvindev15.npm.NpmScript
import io.github.kelvindev15.utils.NpmExec
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

/**
 * The Plugin implementation.
 */
open class Npm : Plugin<Project> {
    private val tasksGroup = "Npm"

    override fun apply(target: Project) {
        val extension = target.extensions.create<PackageJsonExtension>("packageJson")
        val generatePackage = target.tasks.register<GeneratePackageFileTask>("generatePackageJson") {
            group = tasksGroup
            packageJson.set(
                NpmPackageFile(
                    name = extension.name.orNull,
                    version = extension.version.orNull,
                    author = extension.author.orNull,
                    description = extension.description.orNull,
                    main = extension.main.orNull,
                    license = extension.license.orNull,
                    scripts = extension.scripts.get().map { NpmScript(it.scriptName, it.command) },
                    dependencies = extension.dependencies.get().map { NpmDependency(it.first, it.second) },
                    devDependencies = extension.devDependencies.get().map { NpmDependency(it.first, it.second) },
                    repository = extension.repository.orNull?.let { NpmRepository(it.first, it.second) },
                    homepage = extension.homepage.orNull,
                ),
            )
        }

        val install = target.tasks.register<NpmTask>("npmInstall") {
            logger.debug("NpmExec.bin: ${NpmExec.bin}")
            group = tasksGroup
            dependsOn(generatePackage)
            outputs.dir(project.layout.projectDirectory.dir("node_modules"))
            outputs.file(project.layout.projectDirectory.file("package-lock.json"))
            arguments("install")
        }

        target.tasks.register<NpmTask>("generatePackageLock") {
            group = tasksGroup
            dependsOn(generatePackage)
            outputs.files(target.layout.projectDirectory.file("package-lock.json"))
            arguments("install", "--package-lock-only")
        }

        target.afterEvaluate {
            extension.scripts.get().forEach { s ->
                target.tasks.register<NpmScriptTask>("npm${s.scriptName.capitalized()}") {
                    group = tasksGroup
                    dependsOn(install)
                    dependsOn(*s.taskDependencies.map { it.asGradleTask(target) }.toTypedArray())
                    script.set(s.scriptName)
                    s.configureTask(this)
                }
            }
        }
    }
}
