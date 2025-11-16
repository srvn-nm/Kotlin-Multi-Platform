package com.example.kmp.plugins


import io.ktor.server.application.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        // configure Kotlinx JSON; ignore unknown keys for forward compatibility
        json(Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        })
    }
}
