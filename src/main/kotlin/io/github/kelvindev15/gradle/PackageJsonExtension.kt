package io.github.kelvindev15.gradle

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import java.io.Serializable

/**
 * The package json extension facility.
 */
open class PackageJsonExtension(private val project: Project) : Serializable {

    /**
     * The name of the package.
     */
    val name: Property<String> = project.objects.property<String>()

    /**
     * The version of the package.
     */
    val version: Property<String> = project.objects.property<String>().also {
        it.convention("1.0.0")
    }

    /**
     * The author of the package.
     */
    val author: Property<String> = project.objects.property<String>().also {
        it.convention("Kelvin Olaiya")
    }

    /**
     * The description of the package.
     */
    val description: Property<String> = project.objects.property()

    /**
     * The main file of the package.
     */
    val main: Property<String> = project.objects.property()

    /**
     * The license of the package.
     */
    val license: Property<String> = project.objects.property()

    /**
     * The scripts of the package.
     */
    val scripts: ListProperty<NpmScript> = project.objects.listProperty<NpmScript>().also {
        it.convention(emptyList())
    }

    /**
     * The dependencies of the package.
     */
    val dependencies: ListProperty<Pair<String, String>> = project.objects.listProperty<Pair<String, String>>().also {
        it.convention(emptyList())
    }

    /**
     * The dev dependencies of the package.
     */
    val devDependencies: ListProperty<Pair<String, String>> =
        project.objects.listProperty<Pair<String, String>>().also {
            it.convention(emptyList())
        }

    /**
     * The repository of the package.
     */
    val repository: Property<Pair<String, String>> = project.objects.property()

    /**
     * The homepage of the package.
     */
    val homepage: Property<String> = project.objects.property<String>().also {
        it.convention("kelvin-olaiya.github.io")
    }

    /**
     * A dependency builder for package dependencies.
     */
    inner class DependencyBuilder(private val type: ListProperty<Pair<String, String>>) {

        /**
         * Add a dependency to the package.
         */
        infix fun String.version(v: String) = type.add(Pair(this, v))
    }

    /**
     * A script dependency for package scripts.
     *
     * [taskName] The name of the script.
     * [projectName] The name of the project in which the script is defined.
     */
    inner class NpmTaskDependency(
        val taskName: String,
        val projectName: String? = null,
    ) {

        /**
         * Get the script as a Gradle task.
         */
        fun asGradleTask(project: Project): Task =
            projectName?.let {
                project.project(":$it").tasks.named(taskName).get()
            } ?: project.tasks.named(taskName).get()
    }

    /**
     * A package script.
     *
     * [scriptName] The name of the script.
     * [command] The command to run.
     * [taskDependencies] The dependencies of the script.
     */
    data class NpmScript(
        val scriptName: String,
        val command: String,
        val taskDependencies: Set<NpmTaskDependency> = emptySet(),
    )

    /**
     * A script builder for package scripts.
     */
    inner class ScriptBuilder {

        /**
         * Add a script to the package.
         */
        infix fun String.runs(script: String): NpmScript = NpmScript(this, script)

        /**
         * Add set the script dependencies.
         */
        infix fun NpmScript.dependingOn(deps: List<NpmTaskDependency>) =
            NpmScript(scriptName, command, taskDependencies + deps)

        /**
         * Create a task dependency.
         */
        fun task(name: String, project: String? = null) = NpmTaskDependency(name, project)

        /**
         * Create a task dependency for an npm script.
         */
        fun npmScript(name: String, project: String? = null) = task("npm${name.capitalized()}", project)

        /**
         * Set the project for a task dependency.
         */
        infix fun NpmTaskDependency.inProject(project: String) = NpmTaskDependency(this.taskName, project)

        /**
         * Add a script to the package.
         */
        fun script(s: NpmScript) = scripts.add(s)
    }

    /**
     * Add a dependency to the package.
     */
    fun dependencies(apply: DependencyBuilder.() -> Unit) {
        DependencyBuilder(dependencies).apply()
    }

    /**
     * Add a dev dependency to the package.
     */
    fun devDependencies(apply: DependencyBuilder.() -> Unit) {
        DependencyBuilder(devDependencies).apply()
    }

    /**
     * Add a script to the package.
     */
    fun scripts(apply: ScriptBuilder.() -> Unit) {
        ScriptBuilder().apply()
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
