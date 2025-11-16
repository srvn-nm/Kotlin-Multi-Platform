package com.example.kmp.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

/**
 * Reads JWT settings from application.conf (ktor.security.jwt.*) or env vars:
 *  - JWT_SECRET
 *  - JWT_ISSUER
 *  - JWT_AUDIENCE
 *
 * Installs Ktor Authentication with a named JWT provider "auth-jwt".
 * The validate block verifies the claim "username" exists.
 */
fun Application.configureSecurity() {

    val jwtSecret =
        environment.config.propertyOrNull("ktor.security.jwt.secret")?.getString()
            ?: System.getenv("JWT_SECRET")
            ?: throw IllegalStateException("JWT secret not provided. Set ktor.security.jwt.secret or env JWT_SECRET")

    val jwtIssuer =
        environment.config.propertyOrNull("ktor.security.jwt.issuer")?.getString()
            ?: System.getenv("JWT_ISSUER")
            ?: "com.example.kmp"

    val jwtAudience =
        environment.config.propertyOrNull("ktor.security.jwt.audience")?.getString()
            ?: System.getenv("JWT_AUDIENCE")
            ?: "com.example.kmp.audience"

    val jwtRealm =
        environment.config.propertyOrNull("ktor.security.jwt.realm")?.getString()
            ?: "Access to 'me'"

    val algorithm = Algorithm.HMAC256(jwtSecret)

    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(
                JWT
                    .require(algorithm)
                    .withIssuer(jwtIssuer)
                    .withAudience(jwtAudience)
                    .build()
            )
            validate { credential ->
                val username = credential.payload.getClaim("username").asString()
                if (!username.isNullOrBlank()) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
}