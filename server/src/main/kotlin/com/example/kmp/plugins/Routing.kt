package com.example.kmp.plugins

import com.example.kmp.database.LoginRequest
import com.example.kmp.database.LoginResponse
import com.example.kmp.database.RegisterRequest
import com.example.kmp.database.User
import com.example.kmp.database.UserRepo
import com.example.kmp.exceptions.BadRequestException
import com.example.kmp.plugins.JwtConfig.makeToken
import com.example.kmp.routes.authRoutes
import com.example.kmp.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Configure application routing (auth endpoints and a protected example).
 * This assumes UserRepo has register/authenticate implemented.
 */
fun Application.configureRouting() {

    val userRepo = UserRepo() // make sure class exists and imported

    routing {

        get("/") {
            call.respondText("Hello World!")
        }

        get("/health") {
            call.respond(mapOf("status" to "ok"))
        }

        post("/auth/register") {
            val req = call.receive<RegisterRequest>()
            // UserRepo.register should hash password before storing
            val user = userRepo.register(req)
            call.respond(mapOf("id" to user.id, "username" to user.username))
        }

        post("/auth/login") {
            // parse request body
            val req = call.receive<LoginRequest>()

            // authenticate returns User? (null if credentials wrong)
            val user: User = userRepo.authenticate(req.username, req.password)
                ?: throw BadRequestException("Invalid username or password")

            // read jwt config from application.conf or env
            val jwtConfig = environment.config.config("ktor.security.jwt")
            val secret = jwtConfig.property("secret").getString()
            val issuer = jwtConfig.property("issuer").getString()
            val audience = jwtConfig.property("audience").getString()

            // create token using helper (JwtConfig from earlier messages)
            val token = makeToken(
                username = user.username,
                secret = secret,
                issuer = issuer,
                audience = audience
            )

            val response = LoginResponse(token = token)
            call.respond(response)
        }

        // pass repo + environment
        authRoutes(userRepo, environment)

        // pass repo
        userRoutes(userRepo)

        // Protected example
        authenticate("auth-jwt") {
            get("/me") {
                val principal = call.principal<JWTPrincipal>()!!
                val username = principal.payload.getClaim("username").asString()
                call.respond(mapOf("username" to username))
            }
        }
    }
}