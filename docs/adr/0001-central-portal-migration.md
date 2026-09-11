# Migrate Maven Central publishing from OSSRH to the Central Portal

Sonatype fully retired legacy OSSRH (`s01.oss.sonatype.org`) on 2025-06-30; every CI run on
this repo was failing with `createStagingRepositoryOnMavenCentral` returning HTTP 402 from
the dead endpoint, on every branch (not just master), since `should-deploy` has no branch
restriction. We bumped `publish-on-central` (which dropped OSSRH/Nexus support entirely in
9.x) and switched the deploy pipeline to the Portal-native tasks
(`publishAllPublicationsToProjectLocalRepository`, `zipMavenCentralPortalPublication`,
`releaseMavenCentralPortalPublication`), reading credentials from the
`MAVEN_CENTRAL_PORTAL_USERNAME`/`PASSWORD` org secrets.

We deliberately pinned the version at **9.1.1**, not the latest 9.x, because 9.1.2+ bumps
the plugin's internal Kotlin dependency to 2.2.0, which needs a newer Gradle/Kotlin runtime
than Gradle 8 (still in use here) provides — that surfaces as
`NoClassDefFoundError: kotlin/coroutines/jvm/internal/SpillingKt`. The pin should be lifted
once the separate Gradle 8→9 migration lands.
