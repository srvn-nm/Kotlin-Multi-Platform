package com.example.kmp.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {

    const val BASE_URL = "http://10.0.2.2:8080" // Android emulator

    private var token: String? = null

    val http = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

        install(Auth) {
            bearer {
                loadTokens {
                    token?.let {
                        BearerTokens(
                            accessToken = it,
                            refreshToken = it
                        )
                    }
                }
            }
        }
    }

    fun setToken(t: String) {
        token = t
    }

    suspend inline fun <reified T> get(path: String): T {
        return http.get("$BASE_URL$path").body()
    }

    suspend inline fun <reified Req, reified Res> post(path: String, body: Req): Res {
        return http.post("$BASE_URL$path") {
            setBody(body)
        }.body()
    }
}
