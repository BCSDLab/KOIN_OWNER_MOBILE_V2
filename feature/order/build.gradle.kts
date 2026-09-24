import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.metro)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    android {
        namespace = "in.koreatech.business.feature.order"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions.jvmTarget = JvmTarget.JVM_11
    }
    iosArm64()
    iosSimulatorArm64()

    sourceSets.commonMain.dependencies {
        implementation(libs.metrox.viewmodel.compose)
        implementation(project(":core:common"))
        implementation(project(":core:di"))
        implementation(project(":core:designsystem"))
        implementation(project(":core:navigation"))
        implementation(project(":domain"))
        implementation(libs.compose.runtime)
        implementation(libs.compose.foundation)
        implementation(libs.compose.material3)
        implementation(libs.compose.ui)
        implementation(libs.compose.components.resources)
        implementation(libs.androidx.navigation3.runtime)
        implementation(libs.androidx.lifecycle.viewmodelCompose)
        implementation(libs.orbit.core)
        implementation(libs.orbit.viewmodel)
        implementation(libs.orbit.compose)
        implementation(libs.kotlinx.collections.immutable)
    }
}
