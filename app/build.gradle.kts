plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.aldiprahasta.storyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aldiprahasta.storyapp"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    val lifecycleVersion = "2.8.0"
    val retrofitVersion = "2.11.0"
    val roomVersion = "2.6.1"
    val pagingVersion = "3.3.0"
    val coreTesting = "2.1.0"
    val kotlinxCoroutinesTest = "1.6.1"
    val mockitoCore = "4.4.0"
    val mockitoInline = "4.4.0"

    //lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    //network
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //coil
    implementation("io.coil-kt:coil:2.6.0")

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    //koin
    implementation("io.insert-koin:koin-android:3.5.6")

    //timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    //room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //paging
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:$coreTesting")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesTest")
    testImplementation("org.mockito:mockito-inline:$mockitoInline")
    testImplementation("org.mockito:mockito-core:$mockitoCore")
    testImplementation("app.cash.turbine:turbine:0.12.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.arch.core:core-testing:$coreTesting")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesTest")


}