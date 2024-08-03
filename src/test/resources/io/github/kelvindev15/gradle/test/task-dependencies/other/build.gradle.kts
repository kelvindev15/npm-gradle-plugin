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
        script("otherTask" runs "echo other")
    }
    repository.set("git" to "http://example.com")
}
