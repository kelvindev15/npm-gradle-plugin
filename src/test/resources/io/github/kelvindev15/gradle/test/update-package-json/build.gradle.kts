plugins {
    id("io.github.kelvindev15.npm-gradle-plugin")
}

packageJson {
    author.set("Kelvin Olaiya")
    name.set("test-package")
    version.set("1.0.0")
    description.set("Just a test")
    main.set("index.js")
    license.set("MIT")
    scripts {
        script("build" runs "echo build")
    }
    dependencies.set(
        listOf("express" to "^4.20.1", "body-parser" to "^1.19.0")
    )
    devDependencies.set(
        listOf("nodemon" to "^2.0.7")
    )
    repository.set("git" to "https://github.com/kelvindev15/npm-gradle-plugin")
    homepage.set("test.github.io")
}
