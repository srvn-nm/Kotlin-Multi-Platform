package com.example.kmp.exceptions

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.*
import io.ktor.server.plugins.statuspages.*
import javax.security.sasl.AuthenticationException


/**
 * Centralized error -> HTTP mapping.
 * Map your domain exceptions to appropriate HTTP codes and JSON response shape.
 */
fun Application.configureErrorHandling() {
    install(StatusPages) {

        // Custom API exceptions (your domain exceptions)
        exception<BadRequestException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to (cause.message ?: "Bad request")))
        }

        exception<NotFoundException> { call, cause ->
            call.respond(HttpStatusCode.NotFound, mapOf("error" to (cause.message ?: "Not found")))
        }

        // Database/exposed exceptions
        exception<ExposedSQLException> { call, cause ->
            // optionally inspect cause.message for constraint violations and map to 400
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Database error", "details" to (cause.message ?: "")))
        }

        // JWT verification errors (Auth0 lib)
        exception<com.auth0.jwt.exceptions.JWTVerificationException> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid or expired token"))
        }

        // Generic fallback
        exception<Throwable> { call, cause ->
            // log stacktrace server-side (stdout or logback)
            cause.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (cause.message ?: "Internal server error")))
        }
    }
}
