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
    scripts.set(
        listOf(
            "build" to "true",
            "test" to "echo \"Error: no test specified\" && exit 1",
            "lint" to "echo \"I'm executing a lint command\"",
        )
    )
    repository.set("git" to "https://github.com/kelvindev15/npm-gradle-plugin")
    homepage.set("kelvin-olaiya.github.io")
}
