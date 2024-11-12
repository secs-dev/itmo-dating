package ru.ifmo.se.dating.authik.security.auth

import ru.ifmo.se.dating.security.auth.AccessToken

interface TokenIssuer {
    fun issue(payload: AccessToken.Payload): AccessToken
}
