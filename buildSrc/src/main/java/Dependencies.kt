object Versions {
    const val androidApp = "8.2.0"
    const val jetbrains = "1.9.10"
    const val core = "1.12.0"
    const val appcompat = "1.6.1"
    const val material = "1.11.0"
    const val constrainLayout = "2.1.4"
    const val nav_fragment = "2.7.7"
    const val nav_ui = "2.7.7"
    const val hilt = "2.46"
    const val hilt_compiler = "2.46"
    const val safeArgs = "2.7.3"
}

object Libs {

    object Ui {
        const val core = "androidx.core:core-ktx:${Versions.core}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val constrainLayout = "androidx.constraintlayout:constraintlayout:${Versions.constrainLayout}"
    }

    object Hilt {
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hilt_compiler = "com.google.dagger:hilt-compiler:${Versions.hilt_compiler}"
    }

    object Nav {
        const val nav_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_fragment}"
        const val nav_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav_ui}"
    }

}

object Plugins {
    const val androidApp = "com.android.application"
    const val jetbrains = "org.jetbrains.kotlin.android"
    const val android = "com.android.library"
    const val hilt = "com.google.dagger.hilt.android"
    const val kotlinKapt = "kotlin-kapt"
    const val safeArgs = "androidx.navigation.safeargs"
}



