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
        "test" runs "echo \"Error: no test specified\" && exit 1"
    }
    dependencies {
        "express" version "^4.17.1"
        "webpack" version "^5.0.0"
    }
    devDependencies {
        "nodemon" version "^2.0.7"
    }
    repository = ("git" to "https://github.com/kelvindev15/npm-gradle-plugin")
    homepage = "kelvin-olaiya.github.io"
}