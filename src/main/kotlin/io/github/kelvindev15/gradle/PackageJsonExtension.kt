package io.github.kelvindev15.gradle

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import java.io.Serializable

/**
 * The package json extension facility.
 */
open class PackageJsonExtension(objects: ObjectFactory) : Serializable {

    /**
     * The name of the package.
     */
    val name: Property<String> = objects.property<String>()

    /**
     * The version of the package.
     */
    val version: Property<String> = objects.property<String>().also {
        it.convention("1.0.0")
    }

    /**
     * The author of the package.
     */
    val author: Property<String> = objects.property<String>().also {
        it.convention("Kelvin Olaiya")
    }

    /**
     * The description of the package.
     */
    val description: Property<String> = objects.property()

    /**
     * The main file of the package.
     */
    val main: Property<String> = objects.property()

    /**
     * The license of the package.
     */
    val license: Property<String> = objects.property()

    /**
     * The scripts of the package.
     */
    val scripts: ListProperty<Pair<String, String>> = objects.listProperty<Pair<String, String>>().also {
        it.convention(emptyList())
    }

    /**
     * The dependencies of the package.
     */
    val dependencies: ListProperty<Pair<String, String>> = objects.listProperty<Pair<String, String>>().also {
        it.convention(emptyList())
    }

    /**
     * The dev dependencies of the package.
     */
    val devDependencies: ListProperty<Pair<String, String>> = objects.listProperty<Pair<String, String>>().also {
        it.convention(emptyList())
    }

    /**
     * The repository of the package.
     */
    val repository: Property<Pair<String, String>> = objects.property()

    /**
     * The homepage of the package.
     */
    val homepage: Property<String> = objects.property<String>().also {
        it.convention("kelvin-olaiya.github.io")
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
