plugins {
    kotlin("plugin.serialization")
    id("multiplatform.tier3")
    id("published")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            api(libs.kotlinx.datetime)
            api(libs.ink.ui.structures)
            api(libs.ink.ui.render.remote)
            api(libs.ink.spondee)
        }
    }
}
