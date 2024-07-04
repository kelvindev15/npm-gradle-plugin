package io.github.kelvindev15.gradle

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import java.io.Serializable

/**
 * ff.
 */
open class PackageJsonExtension(objects: ObjectFactory) : Serializable {

    val name: Property<String> = objects.property<String>()
    val version: Property<String> = objects.property<String>().also {
        it.convention("1.0.0")
    }
    val author: Property<String> = objects.property<String>().also {
        it.convention("Kelvin Olaiya")
    }
    val description: Property<String> = objects.property()
    val main: Property<String> = objects.property()
    val license: Property<String> = objects.property()
    val scripts: ListProperty<Pair<String, String>> = objects.listProperty()
    val dependencies: ListProperty<Pair<String, String>> = objects.listProperty()
    val devDependencies: ListProperty<Pair<String, String>> = objects.listProperty()
    val repository: Property<Pair<String, String>> = objects.property()
    val homepage: Property<String> = objects.property<String>().also {
        it.convention("kelvin-olaiya.github.io")
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
