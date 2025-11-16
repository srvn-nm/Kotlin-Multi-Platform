plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "com.example.kmp"
version = "1.0.0"
application {
    mainClass.set("com.example.kmp.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {

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

    // Testing (optional)
    testImplementation(kotlin("test"))
    implementation(projects.shared)
    implementation(libs.logback)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)

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
}