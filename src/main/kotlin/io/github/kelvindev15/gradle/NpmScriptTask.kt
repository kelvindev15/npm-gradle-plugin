package io.github.kelvindev15.gradle

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property

/**
 * The task to run a npm command.
 */
open class NpmScriptTask : NpmTask() {

    /**
     * The script to run.
     */
    @Input
    val script: Property<String> = project.objects.property<String>()

    @TaskAction
    override fun exec() {
        arguments("run", script.get())
        super.exec()
    }
}
