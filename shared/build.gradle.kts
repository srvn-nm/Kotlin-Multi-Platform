import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    iosArm64()
    iosSimulatorArm64()
    
    jvm()
    
    js {
        browser()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            // Authentication JWT
            implementation(libs.ktor.server.auth.jwt.jvm)
            implementation(libs.ktor.server.auth.jvm)

            // Logging
            implementation(libs.logback)

            // Database: Exposed + HikariCP + Postgres driver
            implementation(libs.hikaricp)
            implementation(libs.postgresql)

            // Password hashing (bcrypt) - favre library
            implementation(libs.bcrypt)

            implementation(libs.ktor.server.error.handler)

            implementation(libs.ktor.server.auth)
            implementation(libs.ktor.server.auth.jwt)

            implementation(libs.ktor.server.core.jvm.v300)
            implementation(libs.ktor.server.netty.jvm.v300)

            // JSON
            implementation(libs.ktor.serialization.kotlinx.json.jvm.v300)
            implementation(libs.ktor.server.content.negotiation.jvm.v300)

            // Exposed ORM
            implementation(libs.exposed.core.v0560)
            implementation(libs.exposed.dao.v0560)
            implementation(libs.exposed.jdbc.v0560)

            implementation(libs.h2)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.auth.resources)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.kmp.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
