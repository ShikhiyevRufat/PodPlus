object Versions {
    const val androidApp = "8.2.2"
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
    const val firebase_bom = "32.8.0"
    const val google_services = "4.4.1"
    const val glide = "4.16.0"
    const val player = "1.3.1"
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

    object Firebase {
        const val firebase_auth = "com.google.firebase:firebase-auth"
        const val firebase_store = "com.google.firebase:firebase-firestore"
        const val firebase_bom = "com.google.firebase:firebase-bom:${Versions.firebase_bom}"
        const val firebase_analytics = "com.google.firebase:firebase-analytics"
    }

    object GetImage {
        const val bumpTechGlide = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object Player {
        const val media3_exoplayer = "androidx.media3:media3-exoplayer:${Versions.player}"
        const val media3_exoplayer_dash = "androidx.media3:media3-exoplayer-dash:${Versions.player}"
        const val media3_ui = "androidx.media3:media3-ui:${Versions.player}"
    }
}


object Plugins {
    const val androidApp = "com.android.application"
    const val jetbrains = "org.jetbrains.kotlin.android"
    const val android = "com.android.library"
    const val hilt = "com.google.dagger.hilt.android"
    const val kotlinKapt = "kotlin-kapt"
    const val safeArgs = "androidx.navigation.safeargs"
    const val google_services = "com.google.gms.google-services"
}



