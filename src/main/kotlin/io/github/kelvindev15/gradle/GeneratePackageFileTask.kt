package io.github.kelvindev15.gradle

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.github.kelvindev15.npm.NpmDependency
import io.github.kelvindev15.npm.NpmPackageFile
import io.github.kelvindev15.npm.NpmRepository
import io.github.kelvindev15.npm.NpmScript
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import java.io.File

/**
 * Just a template.
 */
open class GeneratePackageFileTask : DefaultTask() {

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

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
    val scripts: ListProperty<Pair<String, String>> = project.objects.listProperty()

    /**
     * the dependencies of the package.
     */
    @Input
    val dependencies: ListProperty<Pair<String, String>> = project.objects.listProperty()

    /**
     * the devDependencies of the package.
     */
    @Input
    val devDependencies: ListProperty<Pair<String, String>> = project.objects.listProperty()

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
            scripts = NpmScript.from(*scripts.get().toTypedArray()),
            dependencies = NpmDependency.from(*dependencies.get().toTypedArray()),
            devDependencies = NpmDependency.from(*devDependencies.get().toTypedArray()),
            repository = NpmRepository.from(repository.get()),
            homepage = homepage.get(),
        ).toMap()
        File(project.projectDir, "package.json").also {
            it.createNewFile()
            it.writeText("${gson.toJson(packageFile)}\n", Charsets.UTF_8)
        }
    }
}
