plugins {
    id(Plugins.androidApp)
    id(Plugins.jetbrains)
    id(Plugins.hilt)
    id(Plugins.kotlinKapt)
    id(Plugins.safeArgs)
    id(Plugins.google_services)
}

android {
    namespace = "com.example.podplus"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.podplus"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release"){
            keyAlias = "key0"
            keyPassword = "Shikhiyevrufat2004."
            storeFile = file("../certificates/PodPlusApkKey.jks")
            storePassword = "Shikhiyevrufat2004."
        }

        create("dev"){
            keyAlias = "key0"
            keyPassword = "Shikhiyevrufat2004."
            storeFile = file("../certificates/DebugPodPlusApkKey.jks")
            storePassword = "Shikhiyevrufat2004."
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        debug {
            isDebuggable = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("dev")
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
    implementation(project(":feature:splashscreen"))
    implementation(project(":feature:pages"))
    implementation(project(":feature:onboarding"))

    implementation(Libs.Firebase.firebase_store)
    implementation(Libs.Hilt.hilt)
    kapt(Libs.Hilt.hilt_compiler)
    implementation(Libs.Ui.appcompat)
    implementation(Libs.Ui.constrainLayout)
    implementation(Libs.Nav.nav_fragment)
    implementation(Libs.Nav.nav_ui)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}