package ru.ifmo.se.dating.security.key

import org.junit.Assert.assertEquals
import org.junit.Test
import java.security.KeyPairGenerator
import javax.crypto.KeyGenerator

class KeysTest {
    @Test
    fun secretRoundTrip() {
        for (i in 0..32) {
            val key = KeyGenerator.getInstance("AES").generateKey()
            assertEquals(key, Keys.serialize(key).let { Keys.deserializeSecret(it) })
        }
    }

    @Test
    fun publicRoundTrip() {
        for (i in 0..8) {
            val pair = KeyPairGenerator.getInstance("RSA").genKeyPair()
            assertEquals(pair.public, Keys.serialize(pair.public).let { Keys.deserializePublic(it) })
            assertEquals(pair.private, Keys.serialize(pair.private).let { Keys.deserializePrivate(it) })
        }
    }

    @Test
    fun generateKeys() {
        val aes = KeyGenerator.getInstance("AES").generateKey()
        val rsa = KeyPairGenerator.getInstance("RSA").genKeyPair()

        println("AES Secret: '${Keys.serialize(aes)}'")
        println("RSA Public: '${Keys.serialize(rsa.public)}'")
        println("RSA Private: '${Keys.serialize(rsa.private)}'")
    }
}
