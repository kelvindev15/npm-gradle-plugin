package io.github.kelvindev15.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

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
    }
}
