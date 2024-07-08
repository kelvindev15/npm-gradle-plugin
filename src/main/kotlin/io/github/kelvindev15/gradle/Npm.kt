package io.github.kelvindev15.gradle

import io.github.kelvindev15.utils.NpmExec
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
            name.set(extension.name)
            version.set(extension.version)
            author.set(extension.author)
            description.set(extension.description)
            main.set(extension.main)
            license.set(extension.license)
            scripts.set(extension.scripts)
            dependencies.set(extension.dependencies)
            devDependencies.set(extension.devDependencies)
            repository.set(extension.repository)
            homepage.set(extension.homepage)
        }

        val install = target.tasks.register<Exec>("npmInstall") {
            logger.debug("NpmExec.bin: ${NpmExec.bin}")
            group = tasksGroup
            dependsOn(generatePackage)
            outputs.dir(project.layout.projectDirectory.dir("node_modules"))
            outputs.file(project.layout.projectDirectory.file("package-lock.json"))
            commandLine(NpmExec.bin, "install")
        }

        target.tasks.register<Exec>("generatePackageLock") {
            group = tasksGroup
            dependsOn(generatePackage)
            outputs.files(target.layout.projectDirectory.file("package-lock.json"))
            println(NpmExec.bin)
            commandLine(NpmExec.bin, "install", "--package-lock-only")
        }

        val build = target.tasks.register<Exec>("npmBuild") {
            group = tasksGroup
            dependsOn(install)
            inputs.dir(project.layout.projectDirectory.dir("src"))
            commandLine(NpmExec.bin, "run", "build")
        }

        target.tasks.register<Exec>("npmTest") {
            group = tasksGroup
            dependsOn(build)
            commandLine(NpmExec.bin, "run", "test")
        }

        target.tasks.register<Exec>("npmRun") {
            group = tasksGroup
            dependsOn(build)
            commandLine(NpmExec.bin, "run", target.properties["cmd"] as String)
        }
    }
}
