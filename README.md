# Npm Gradle Plugin

This plugin is a wrapper over the npm cli script. The goal is to integrate the usage of npm within gradle so that devs can easily work node project while relying on the power of the gradle build tool.

## How to use

Apply the plugin to your gradle project:

```kotlin
plugins {
    id("io.github.kelvindev15.npm-gradle-plugin") version "<version>"
}
```

Describe how you would like your `package.json` to be. Here's an example:

```kotlin
packageJson {
    author.set("Kelvin Olaiya")
    name.set("test-package")
    version.set("1.0.0")
    description.set("This is just a test package")
    main.set("index.js")
    license.set("MIT")
    scripts.set(
        listOf("greet" to "echo \"Hello, this is the greet script\"")
    )
    dependencies.set(
        listOf("express" to "^4.17.1")
    )
    devDependencies.set(
        listOf("nodemon" to "^2.0.7")
    )
    repository.set("git" to "https://github.com/kelvindev15/npm-gradle-plugin")
    homepage.set("kelvin-olaiya.github.io")
}
```

Now you can run either one of the following tasks based on your need:

* `generatePackageJson`: Generates a **package.json** file base on the provided configuration
* `npmInstall`: runs the `npm install` command. (This task depends on the *generatePackageJson* task)
* `generatePackageLock`: runs the `npm install --package-json-only` command. (This task depends on the *generatePackageJson* task)

The plugin also provide tasks for running npm scripts. 
Based on the previous example the `npmGreet` task will be created.

It is also possible to use an alternative syntax for defining the package.json configuration:

```kotlin
packageJson {
    author = "Kelvin Olaiya"
    name = "test-package"
    version = "1.0.0"
    description = "Just a test"
    main = "index.js"
    license = "MIT"
    scripts {
        "test" runs "echo \"Error: no test specified\" && exit 1"
    }
    dependencies {
        "express" version "^4.17.1"
    }
    devDependencies {
        "nodemon" version "^2.0.7"
    }
    repository = ("git" to "https://github.com/kelvindev15/npm-gradle-plugin")
    homepage = "kelvin-olaiya.github.io"
}
```

You can also specify script dependencies:

```kotlin
scripts {
    script("task1" runs "echo task1")
    script("task2" runs "echo task2")
    script("task3" runs "echo task3" dependingOn listOf(npmScript("task1"), npmScript("task2")))
    script("task4" runs "echo task4" dependingOn listOf(npmScript("otherTask") inProject "other"))
    script("task5" runs "echo task5" dependingOn listOf(npmScript("otherTask", "other")))
    script("task6" runs "echo task6" dependingOn listOf(task("test")))
    script("task7" runs "echo task7" dependingOn listOf(task("test") inProject "other"))
    script("task8" runs "echo task8" dependingOn listOf(task("test", "other")))
}
```
