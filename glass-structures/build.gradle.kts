plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ink.publications)
}

repositories {
    mavenCentral()
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvm()
    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()
    js(IR) {
        nodejs()
        browser {
            testTask {
                useKarma {
                    useFirefoxHeadless()
                }
            }
        }
    }
    linuxX64()
    linuxArm64()
    mingwX64()
    macosX64()
    macosArm64()
    iosSimulatorArm64()
    iosX64()
    iosArm64()
    watchosSimulatorArm64()
    watchosDeviceArm64()
    watchosX64()
    watchosArm32()
    watchosArm64()
    tvosSimulatorArm64()
    tvosX64()
    tvosArm64()

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
