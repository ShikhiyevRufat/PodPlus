plugins {
    id(Plugins.androidApp)
    id(Plugins.jetbrains)
    id(Plugins.hilt)
    id(Plugins.kotlinKapt)
    id(Plugins.safeArgs)
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
    implementation(project(":feature:splashscreen"))
    implementation(project(":feature:pages"))

    implementation(Libs.Hilt.hilt)
    kapt(Libs.Hilt.hilt_compiler)
    implementation(Libs.Ui.core)
    implementation(Libs.Ui.appcompat)
    implementation(Libs.Ui.material)
    implementation(Libs.Ui.constrainLayout)
    implementation(Libs.Nav.nav_fragment)
    implementation(Libs.Nav.nav_ui)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}