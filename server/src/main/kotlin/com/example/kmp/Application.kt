package com.example.kmp

import com.example.kmp.database.DatabaseFactory
import com.example.kmp.exceptions.configureErrorHandling
import com.example.kmp.plugins.configureRouting
import com.example.kmp.plugins.configureSecurity
import com.example.kmp.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty


/**
 * Main entry. Starts Netty server on port configured in application.conf (or env PORT).
 * The module() function below does the wiring.
 */
fun main() {
    embeddedServer(
        Netty,
        port = System.getenv("PORT")?.toIntOrNull() ?: 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

/**
 * Ktor application module - wires all pieces:
 *  - database init
 *  - serialization
 *  - authentication (JWT)
 *  - error handling
 *  - routing (health + auth + example protected route)
 *
 * Keep this file as the top-level orchestrator; move detailed code to other files as you like.
 */
fun Application.module() {
    // 1) Initialize DB (reads config or env variables inside DatabaseFactory)
    DatabaseFactory.init()

    // 2) Configure JSON serialization
    configureSerialization()

    // 3) Configure Authentication (JWT)
    configureSecurity()

    // 4) Configure centralized exception handling (StatusPages)
    configureErrorHandling()

    // 5) Configure application routing (health, auth, protected sample)
    configureRouting()
}