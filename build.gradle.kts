buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}
plugins {
    id(Plugins.androidApp) version Versions.androidApp apply false
    id(Plugins.jetbrains) version Versions.jetbrains apply false
    id(Plugins.hilt) version Versions.hilt apply false
}