pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PodPlus"
include(":app")
include(":domain")
include(":common")
include(":data")
include(":network")
include(":feature")
include(":entities")
include(":feature:splashscreen")
include(":feature:onboarding")
include(":feature:pages")
