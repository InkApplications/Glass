import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("library")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.structures)
                implementation(libs.bundles.ktor.client)
                implementation(libs.ktor.client.cio)
            }
        }
    }
}
