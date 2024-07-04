package io.github.kelvindev15.npm

import com.google.gson.Gson
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register
import java.io.Serializable

/**
 * Just a template.
 */
open class HelloGradle : Plugin<Project> {
    override fun apply(target: Project) {
        // val extension = target.extensions.create<NodeExtension>("node")
        target.tasks.register<GeneratePackageFileTask>("hello") {
            // name.set(extension.author)
        }
    }
}

/**
 * Just a template.
 */
open class GeneratePackageFileTask : DefaultTask() {

    private val gson: Gson = Gson()

    /**
     * the name of the package.
     */
    @Input
    val name: Property<String> = project.objects.property<String>()

    /**
     * the version of the package.
     */
    @Input
    val version: Property<String> = project.objects.property<String>().also {
        it.convention("1.0.0")
    }

    /**
     * the author of the package.
     */
    @Input
    val author: Property<String> = project.objects.property<String>().also {
        it.convention("Kelvin Olaiya")
    }

    /**
     * the description of the package.
     */
    @Input
    val description: Property<String> = project.objects.property()

    /**
     * the main file of the package.
     */
    @Input
    val main: Property<String> = project.objects.property()

    /**
     * the license of the package.
     */
    @Input
    val license: Property<String> = project.objects.property()

    /**
     * the scripts of the package.
     */
    @Input
    val scripts: Property<Map<String, String>> = project.objects.property()

    /**
     * the dependencies of the package.
     */
    @Input
    val dependencies: Property<Map<String, String>> = project.objects.property()

    /**
     * the devDependencies of the package.
     */
    @Input
    val devDependencies: Property<Map<String, String>> = project.objects.property()

    /**
     * the repository of the package.
     */
    @Input
    val repository: Property<Pair<String, String>> = project.objects.property()

    /**
     * the homepage of the package.
     */
    @Input
    val homepage: Property<String> = project.objects.property<String>().also {
        it.convention("kelvin-olaiya.github.io")
    }

    /**
     * the output file.
     */
    @OutputFile
    val output: RegularFileProperty = project.objects.fileProperty().also {
        it.convention(project.layout.projectDirectory.file("package.json"))
    }

    /**
     * Just a template.
     */
    @TaskAction
    fun generatePackageFile() {
        val packageFile = NpmPackageFile(
            name = name.get(),
            version = version.get(),
            author = author.get(),
            description = description.get(),
            main = main.get(),
            license = license.get(),
            scripts = NpmScript.fromMap(scripts.get()),
            dependencies = NpmDependency.fromMap(dependencies.get()),
            devDependencies = NpmDependency.fromMap(devDependencies.get()),
            repository = NpmRepository.from(repository.get()),
            homepage = homepage.get(),
        ).toMap()
        println(gson.toJson(packageFile))
    }
}

/**
 * ff.
 */
open class NodeExtension(objects: ObjectFactory) : Serializable {

    /**
     * ff.
     */
    val npmExecutable: Property<String> = objects.property<String>().also {
        it.convention("npm")
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
