plugins {
    alias(libs.plugins.androidApplication)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.gren2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gren2"
        minSdk = 25
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-analytics")

    //Firestore
    implementation("com.google.firebase:firebase-firestore")

    //Authentication
    implementation("com.google.firebase:firebase-auth")

    //storage
    implementation("com.google.firebase:firebase-storage")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")

    //picasso
    implementation("com.squareup.picasso:picasso:2.8")

}