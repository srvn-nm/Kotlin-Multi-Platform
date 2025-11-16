package com.example.kmp.models

@kotlinx.serialization.Serializable
data class LoginRequest(
    val username: String,
    val password: String
)
