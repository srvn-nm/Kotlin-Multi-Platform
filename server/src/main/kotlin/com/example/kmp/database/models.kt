package com.example.kmp.database

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(val username: String, val password: String)

@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class LoginResponse(val token: String)

data class User(val id: Long, val username: String)

data class TokenResponse(
    val token: String
)
