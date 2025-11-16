package com.example.kmp.repository

import com.example.kmp.models.User
import com.example.kmp.network.ApiClient

class UserRepository {

    suspend fun getUsers(): List<User> {
        return ApiClient.get("/users")
    }

    suspend fun getMe(): User {
        return ApiClient.get("/me")
    }
}
