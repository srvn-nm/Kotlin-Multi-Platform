package com.example.kmp.routes

import com.example.kmp.database.UserRepo
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.userRoutes(userRepo: UserRepo) {

    authenticate("auth-jwt") {

        get("/users") {
            val users = userRepo.getAll()
            call.respond(users)
        }

    }
}

