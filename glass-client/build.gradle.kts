plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.ink.publications)
}

repositories {
    mavenCentral()
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvm()
    macosX64()
    macosArm64()
    iosSimulatorArm64()
    iosX64()

    sourceSets {
        commonMain.dependencies {
            api(libs.glass.structures)
            implementation(libs.iospin.libsodium)
            implementation(libs.bundles.ktor.client)
            implementation(libs.ktor.client.cio)
            api(libs.ink.regolith.init)
            api(libs.ink.regolith.data)
            api(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
