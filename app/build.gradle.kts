plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleProtobuf)
}

android {
    namespace = "com.nrojt.countdownwidget"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nrojt.countdownwidget"
        minSdk = 30
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
    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }

    protobuf {
        protoc {
            artifact = "com.google.protobuf:protoc:4.26.1"
        }
        generateProtoTasks {
            all().forEach { task ->
                task.builtins {
                    create("java") {
                        option("lite")
                    }
                    create("kotlin") {
                        option("lite")
                    }
                }
            }
        }
    }


    dependencies {

        val composeBom = platform("androidx.compose:compose-bom:2024.03.00")
        implementation(composeBom)
        androidTestImplementation(composeBom)

        implementation(libs.androidx.datastore)
        implementation(libs.protobuf.javalite)
        implementation(libs.protobuf.kotlin.lite)

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)

        implementation(libs.androidx.glance)
        implementation(libs.androidx.glance.appwidget)
        implementation(libs.androidx.glance.material3)

        implementation(libs.androidx.material3.android)
        implementation(libs.androidx.ui)
        implementation(libs.androidx.activity.compose)


        implementation(libs.androidx.ui.tooling.preview)
        debugImplementation(libs.androidx.ui.tooling)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
    }
}