import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private object Config {
    const val NAMESPACE = "com.dolgalyovviktor.blots"
    const val COMPILE_SDK = 34
    const val MIN_SDK = 26
    const val TARGET_SDK = COMPILE_SDK
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
    val JAVA_VERSION = JavaVersion.VERSION_17.majorVersion
}

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = Config.NAMESPACE
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        applicationId = Config.NAMESPACE
        minSdk = Config.MIN_SDK
        targetSdk = Config.TARGET_SDK
        versionCode = Config.VERSION_CODE
        versionName = Config.VERSION_NAME

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(Config.JAVA_VERSION)
        targetCompatibility = JavaVersion.toVersion(Config.JAVA_VERSION)
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    java.toolchain.languageVersion.set(JavaLanguageVersion.of(Config.JAVA_VERSION))
    kotlinOptions.jvmTarget = Config.JAVA_VERSION
    kotlinOptions.freeCompilerArgs += listOf(
        "-opt-in=kotlin.ExperimentalUnsignedTypes",
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-opt-in=kotlinx.coroutines.InternalCoroutinesApi",
        "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
        "-opt-in=androidx.compose.runtime.ExperimentalComposeApi",
        "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
        "-opt-in=kotlinx.coroutines.FlowPreview"
    )

    composeOptions.kotlinCompilerExtensionVersion = "1.5.1"
    packagingOptions.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(listOf("--release", Config.JAVA_VERSION))
}


tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = Config.JAVA_VERSION
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}