package ru.ifmo.se.dating.security.auth

interface TokenDecoder {
    suspend fun decode(token: AccessToken): AccessToken.Payload
}
