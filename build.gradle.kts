@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.ink.publishing) apply false
}

gradle.startParameter.excludedTaskNames.add("lint")

tasks.create("zipPublications", Zip::class) {
    from("client/build/repo/")
    from("structures/build/repo/")
    archiveFileName.set("publications.zip")
}
