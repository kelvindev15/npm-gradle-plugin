package io.github.kelvindev15.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register
import java.util.*

/**
 * Just a template.
 */
open class Npm : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create<PackageJsonExtension>("packageJson")
        target.tasks.register<GeneratePackageFileTask>("generatePackageJson") {
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

        target.tasks.register<Exec>("generatePackageLock") {
            outputs.files(target.layout.projectDirectory.file("package-lock.json"))
            commandLine("npm", "install", "--package-lock-only")
        }

        val install = target.tasks.register<Exec>("npmInstall") {
            outputs.dir(project.layout.projectDirectory.dir("node_modules"))
            outputs.file(project.layout.projectDirectory.file("package-lock.json"))
            commandLine("npm", "install")
        }

        val build = target.tasks.register<Exec>("npmBuild") {
            dependsOn(install)
            inputs.dir(project.layout.projectDirectory.dir("src"))
            commandLine("npm", "run", "build")
        }

        target.tasks.register<Exec>("npmTest") {
            dependsOn(build)
            commandLine("npm", "run", "test")
        }

        extension.scripts.get().filter { it.first !in setOf("build", "test") }.forEach {
            target.tasks.register<Exec>("npmRun${it.first.capitalize()}") {
                dependsOn(install)
                commandLine("npm", "run", it.first)
            }
        }
    }

    companion object {
        fun String.capitalize() = replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase(Locale.getDefault())
            } else {
                it.toString()
            }
        }
    }
}
