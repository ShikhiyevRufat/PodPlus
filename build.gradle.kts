buildscript {
    repositories {
        google()
        mavenCentral()
    }

}
plugins {
    id(Plugins.androidApp) version Versions.androidApp apply false
    id(Plugins.android) version Versions.androidApp apply false
    id(Plugins.jetbrains) version Versions.jetbrains apply false
    id(Plugins.hilt) version Versions.hilt apply false
    id(Plugins.google_services) version Versions.google_services apply false
    id(Plugins.safeArgs) version Versions.safeArgs apply false
}