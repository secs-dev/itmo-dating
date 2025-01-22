package ru.ifmo.se.dating.authik.external.telegram

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.commons.codec.digest.HmacUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.ifmo.se.dating.authik.model.generated.TelegramInitDataMessage
import ru.ifmo.se.dating.authik.model.generated.TelegramWebAppInitDataMessage
import ru.ifmo.se.dating.authik.model.generated.TelegramWebAppUserMessage
import ru.ifmo.se.dating.exception.InvalidValueException
import ru.ifmo.se.dating.validation.expect

@Component
class TelegramInitDataParser(
    @Value("\${itmo-dating.telegram.bot-token}")
    telegramToken: String,
) {
    private val jackson = jacksonObjectMapper().apply {
        registerKotlinModule()
    }
    private val algorithm = "HmacSHA256"
    private val hmac =
        HmacUtils(algorithm, HmacUtils(algorithm, "WebAppData").hmac(telegramToken))

    fun parse(initData: TelegramInitDataMessage): TelegramWebAppInitDataMessage {
        expect(hmac.hmacHex(initData.string) == initData.hash, "hashes are not equal")

        val map = parseToMap(initData)

        val user = jackson.readValue(
            map["user"] ?: throw InvalidValueException("no user"),
            TelegramWebAppUserMessage::class.java,
        )

        val authDate = map["auth_date"]?.toLongOrNull()
            ?: throw InvalidValueException("no auth_date or invalid")

        return TelegramWebAppInitDataMessage(
            queryId = map["query_id"],
            user = user,
            authDate = authDate,
            raw = initData.string,
            hash = initData.hash,
        )
    }

    private fun parseToMap(initData: TelegramInitDataMessage): Map<String, String> {
        val map = mutableMapOf<String, String>()
        for (pair in initData.string.split("\n")) {
            val kv = pair.split("=", limit = 2)
            expect(kv.size == 2, "syntax error")
            map[kv[0]] = kv[1]
        }
        return map
    }
}
