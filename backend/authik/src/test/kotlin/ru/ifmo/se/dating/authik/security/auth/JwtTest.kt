package ru.ifmo.se.dating.authik.security.auth

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.ifmo.se.dating.security.auth.AccessToken
import ru.ifmo.se.dating.security.auth.JwtTokenDecoder
import ru.ifmo.se.dating.security.auth.User
import java.security.KeyPairGenerator
import java.time.Clock
import kotlin.time.Duration.Companion.hours

class JwtTest {
    @Test
    fun jwtLifecycle() {
        val rsa = KeyPairGenerator.getInstance("RSA").genKeyPair()

        val clock = Clock.systemUTC()

        val issuer = JwtTokenIssuer(
            clock = clock,
            privateSignKey = rsa.private,
            duration = 10.hours,
        )

        val decoder = JwtTokenDecoder(
            clock = clock,
            publicSignKey = rsa.public,
        )

        val expected = AccessToken.Payload(User.Id(123))
        val actual = issuer.issue(expected).let { decoder.decode(it) }

        assertEquals(expected, actual)
    }
}
