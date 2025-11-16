package com.example.kmp.repository

import com.example.kmp.models.LoginRequest
import com.example.kmp.models.LoginResponse
import com.example.kmp.models.RegisterRequest
import com.example.kmp.models.User
import com.example.kmp.network.ApiClient

class AuthRepository {

    suspend fun register(username: String, password: String): User {
        return ApiClient.post<RegisterRequest, User>(
            "/auth/register",
            RegisterRequest(username, password)
        )
    }

    suspend fun login(username: String, password: String): String {
        val res = ApiClient.post<LoginRequest, LoginResponse>(
            "/auth/login",
            LoginRequest(username, password)
        )
        ApiClient.setToken(res.token)
        return res.token
    }
}
