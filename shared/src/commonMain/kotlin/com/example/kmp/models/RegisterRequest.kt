package com.example.kmp.models

@kotlinx.serialization.Serializable
data class RegisterRequest(
    val username: String,
    val password: String
)
