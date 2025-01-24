plugins {
    id("com.gradle.enterprise") version "3.19.1"
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.0.19"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishOnFailure()
    }
}

gitHooks {
    preCommit {
        tasks("ktlintCheck")
    }
    commitMsg { conventionalCommits() }
    createHooks(true)
}

rootProject.name = "npm-gradle-plugin"
