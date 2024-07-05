plugins {
    kotlin("plugin.serialization")
    id("multiplatform.tier3")
    id("published")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization.json)
                api(libs.kotlinx.datetime)
                api(libs.ink.ui.structures)
            }
        }
    }
}
