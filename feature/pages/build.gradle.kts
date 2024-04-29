plugins {
    id(Plugins.android)
    id(Plugins.jetbrains)
    id(Plugins.hilt)
    id(Plugins.kotlinKapt)
    id(Plugins.safeArgs)
    id(Plugins.google_services)
}

android {
    namespace = "com.example.pages"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":entities"))

    implementation(Libs.Refresh.refresh)
    implementation(Libs.Player.media3_exoplayer)
    implementation(Libs.Player.media3_exoplayer_dash)
    implementation(Libs.Player.media3_ui)
    implementation(Libs.GetImage.bumpTechGlide)
    annotationProcessor(Libs.GetImage.glideCompiler)
    implementation(Libs.Firebase.firebase_storage)
    implementation(Libs.Firebase.firebase_analytics)
    implementation(Libs.Firebase.firebase_store)
    implementation(Libs.Firebase.firebase_auth)
    implementation(platform(Libs.Firebase.firebase_bom))
    implementation(Libs.Hilt.hilt)
    kapt(Libs.Hilt.hilt_compiler)
    implementation(Libs.Ui.core)
    implementation(Libs.Ui.appcompat)
    implementation(Libs.Ui.material)
    implementation(Libs.Nav.nav_fragment)
    implementation(Libs.Nav.nav_ui)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}