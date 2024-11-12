package ru.ifmo.se.dating.security.key

import org.junit.Assert.assertEquals
import org.junit.Test
import java.security.KeyPairGenerator
import javax.crypto.KeyGenerator

class KeysTest {
    @Test
    fun secretRoundTrip() {
        repeat(32) {
            val key = KeyGenerator.getInstance("AES").generateKey()
            assertEquals(key, Keys.serialize(key).let { Keys.deserializeSecret(it) })
        }
    }

    @Test
    fun publicRoundTrip() {
        repeat(8) {
            val pair = KeyPairGenerator.getInstance("RSA").genKeyPair()
            val (public, private) = pair.public to pair.private
            assertEquals(public, Keys.deserializePublic(Keys.serialize(public)))
            assertEquals(private, Keys.deserializePrivate(Keys.serialize(private)))
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
