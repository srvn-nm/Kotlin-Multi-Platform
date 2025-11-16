package com.example.kmp.routes

import com.example.kmp.database.LoginRequest
import com.example.kmp.database.LoginResponse
import com.example.kmp.database.RegisterRequest
import com.example.kmp.database.UserRepo
import com.example.kmp.plugins.JwtConfig.makeToken
import io.ktor.server.application.ApplicationEnvironment
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.authRoutes(userRepo: UserRepo, env: ApplicationEnvironment) {

    post("/auth/register") {
        val req = call.receive<RegisterRequest>()
        val user = userRepo.createUser(req.username, req.password)
        call.respond(mapOf("id" to user.id, "username" to user.username))
    }

    post("/auth/login") {
        val req = call.receive<LoginRequest>()

        val user = userRepo.authenticate(req.username, req.password)
            ?: throw BadRequestException("Invalid username or password")

        val jwtConfig = env.config.config("ktor.security.jwt")
        val secret = jwtConfig.property("secret").getString()
        val issuer = jwtConfig.property("issuer").getString()
        val audience = jwtConfig.property("audience").getString()

        val token = makeToken(
            username = user.username,
            secret = secret,
            issuer = issuer,
            audience = audience
        )

        call.respond(LoginResponse(token))
    }
}
