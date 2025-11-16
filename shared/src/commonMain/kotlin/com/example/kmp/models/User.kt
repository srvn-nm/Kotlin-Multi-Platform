package com.example.kmp.models

@kotlinx.serialization.Serializable
data class User(
    val id: Long,
    val username: String
)
