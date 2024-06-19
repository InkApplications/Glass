plugins {
    `kotlin-dsl`
}
repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.compose.gradle)
    implementation(libs.compose.compiler)
    implementation(libs.kotlin.gradle)
    implementation(libs.dokka)
    implementation(libs.kotlinx.binary.compatibility)
    implementation(libs.kotlinx.serialization.gradle)
}
