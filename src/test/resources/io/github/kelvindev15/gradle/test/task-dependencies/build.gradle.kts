plugins {
    id("io.github.kelvindev15.npm-gradle-plugin")
}

packageJson {
    author = "Kelvin Olaiya"
    name = "test-package"
    version = "1.0.0"
    description = "Just a test"
    main = "index.js"
    license = "MIT"
    scripts {
        script("task1" runs "echo task1")
        script("task2" runs "echo task2")
        script("task3" runs "echo task3" dependingOn listOf(npmScript("task1"), npmScript("task2")))
        script("task4" runs "echo task4" dependingOn listOf(npmScript("otherTask") inProject "other"))
    }
    repository = ("git" to "https://github.com/kelvindev15/npm-gradle-plugin")
    homepage = "kelvin-olaiya.github.io"
}
