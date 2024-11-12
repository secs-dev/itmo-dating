package ru.ifmo.se.dating.security.auth

import io.jsonwebtoken.Claims

object Jwt {
    private const val USER_ID = "user_id"

    fun serialize(payload: AccessToken.Payload): Map<String, Any> = mapOf(
        USER_ID to payload.userId.number,
    )

    fun deserialize(claims: Claims): AccessToken.Payload {
        val userId = User.Id(claims[USER_ID]!! as Int)
        return AccessToken.Payload(userId)
    }
}
