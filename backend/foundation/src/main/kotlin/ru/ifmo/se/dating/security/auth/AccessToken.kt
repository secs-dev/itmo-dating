package ru.ifmo.se.dating.security.auth

@JvmInline
value class AccessToken(val text: String) {
    data class Payload(val userId: User.Id)
}
