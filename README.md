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

The plugin also provide additional utility task such as:

* `npmBuild`: runs the `npm run build` command.
* `npmTest`: runs the `npm run test` command.
* `npmRun -Pcmd=<custom-command>`: run the `npm run <custom-command>` command.
