package io.github.kelvindev15.gradle

import io.github.kelvindev15.npm.NpmDependency
import io.github.kelvindev15.npm.NpmPackageFile
import io.github.kelvindev15.npm.NpmRepository
import io.github.kelvindev15.npm.NpmScript
import io.github.kelvindev15.utils.NpmExec
import io.github.kelvindev15.utils.Platform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec
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
                    scripts = extension.scripts.get().map { NpmScript(it.first, it.second) },
                    dependencies = extension.dependencies.get().map { NpmDependency(it.first, it.second) },
                    devDependencies = extension.devDependencies.get().map { NpmDependency(it.first, it.second) },
                    repository = extension.repository.orNull?.let { NpmRepository(it.first, it.second) },
                    homepage = extension.homepage.orNull,
                ),
            )
        }

        val install = target.tasks.register<Exec>("npmInstall") {
            logger.debug("NpmExec.bin: ${NpmExec.bin}")
            group = tasksGroup
            dependsOn(generatePackage)
            outputs.dir(project.layout.projectDirectory.dir("node_modules"))
            outputs.file(project.layout.projectDirectory.file("package-lock.json"))
            when (Platform.detectOS()) {
                Platform.OS.WINDOWS -> commandLine("cmd", "/c", NpmExec.bin, "install")
                else -> commandLine(NpmExec.bin, "install")
            }
        }

        target.tasks.register<Exec>("generatePackageLock") {
            group = tasksGroup
            dependsOn(generatePackage)
            outputs.files(target.layout.projectDirectory.file("package-lock.json"))
            when (Platform.detectOS()) {
                Platform.OS.WINDOWS -> commandLine("cmd", "/c", NpmExec.bin, "install", "--package-lock-only")
                else -> commandLine(NpmExec.bin, "install", "--package-lock-only")
            }
        }

        val build = target.tasks.register<Exec>("npmBuild") {
            group = tasksGroup
            dependsOn(install)
            inputs.dir(project.layout.projectDirectory.dir("src"))
            when (Platform.detectOS()) {
                Platform.OS.WINDOWS -> commandLine("cmd", "/c", NpmExec.bin, "run", "build")
                else -> commandLine(NpmExec.bin, "run", "build")
            }
        }

        target.tasks.register<Exec>("npmTest") {
            group = tasksGroup
            dependsOn(build)
            when (Platform.detectOS()) {
                Platform.OS.WINDOWS -> commandLine("cmd", "/c", NpmExec.bin, "run", "test")
                else -> commandLine(NpmExec.bin, "run", "test")
            }
        }

        target.tasks.register<Exec>("npmRun") {
            group = tasksGroup
            dependsOn(build)
            when (Platform.detectOS()) {
                Platform.OS.WINDOWS -> commandLine("cmd", "/c", NpmExec.bin, "run", target.properties["cmd"] as String)
                else -> commandLine(NpmExec.bin, "run", target.properties["cmd"] as String)
            }
        }
    }
}
