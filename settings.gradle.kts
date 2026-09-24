rootProject.name = "KOIN_OWNER"

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":androidApp")
include(":shared")
include(":core:common")
include(":core:di")
include(":core:designsystem")
include(":core:navigation")
include(":data")
include(":domain")
include(":feature:event")
include(":feature:home")
include(":feature:menu")
include(":feature:order")
include(":feature:settings")
include(":feature:signin")
include(":feature:signup")
include(":feature:store")
