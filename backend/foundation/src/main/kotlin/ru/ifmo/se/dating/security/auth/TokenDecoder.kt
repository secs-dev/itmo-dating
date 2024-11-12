package ru.ifmo.se.dating.security.auth

interface TokenDecoder {
    fun decode(token: AccessToken): AccessToken.Payload
}
