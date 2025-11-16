package com.example.kmp.plugins

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object JwtConfig {

    fun makeToken(
        username: String,
        secret: String,
        issuer: String,
        audience: String
    ): String {
        val header = buildJsonObject {
            put("alg", "HS256")
            put("typ", "JWT")
        }.toString()

        val payload = buildJsonObject {
            put("username", username)
            put("iss", issuer)
            put("aud", audience)
            put("exp", (System.currentTimeMillis() / 1000) + (60 * 60)) // 1 hour
        }.toString()

        val headerBase64 = header.encodeBase64()
        val payloadBase64 = payload.encodeBase64()

        val signature = hmacSha256("$headerBase64.$payloadBase64", secret)

        return "$headerBase64.$payloadBase64.$signature"
    }

    private fun hmacSha256(data: String, secret: String): String {
        val hmacKey = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(hmacKey)
        val hash = mac.doFinal(data.toByteArray())
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash)
    }

    private fun String.encodeBase64(): String =
        Base64.getUrlEncoder().withoutPadding().encodeToString(this.toByteArray())
}