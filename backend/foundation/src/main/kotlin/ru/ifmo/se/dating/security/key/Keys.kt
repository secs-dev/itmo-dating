package ru.ifmo.se.dating.security.key

import java.security.Key
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object Keys {
    fun serialize(key: Key): String =
        "${key.algorithm}:${Base64.getEncoder().encodeToString(key.encoded)}"

    fun deserializeSecret(string: String): SecretKey {
        val (algorithm, encodedKey) = split(string)
        return SecretKeySpec(encodedKey, algorithm)
    }

    fun deserializePublic(string: String): PublicKey {
        val (algorithm, encodedKey) = split(string)
        return KeyFactory.getInstance(algorithm).generatePublic(X509EncodedKeySpec(encodedKey))
    }

    fun deserializePrivate(string: String): PrivateKey {
        val (algorithm, encodedKey) = split(string)
        return KeyFactory.getInstance(algorithm).generatePrivate(PKCS8EncodedKeySpec(encodedKey))
    }

    private fun split(string: String): Pair<String, ByteArray> {
        val parts = string.split(":")
        require(parts.size == 2)
        val algorithm = parts[0]
        val encodedKey = parts[1]
        return algorithm to Base64.getDecoder().decode(encodedKey)
    }
}
