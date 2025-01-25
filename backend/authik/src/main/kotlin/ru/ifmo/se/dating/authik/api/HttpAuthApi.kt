package ru.ifmo.se.dating.authik.api

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.authik.api.generated.AuthApiDelegate
import ru.ifmo.se.dating.authik.external.telegram.TelegramInitDataParser
import ru.ifmo.se.dating.authik.logic.AuthService
import ru.ifmo.se.dating.authik.model.generated.AuthGrantMessage
import ru.ifmo.se.dating.authik.model.generated.TelegramInitDataMessage
import ru.ifmo.se.dating.exception.AuthenticationException
import ru.ifmo.se.dating.exception.GenericException
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.text.abbreviated

@Controller
class HttpAuthApi(
    private val telegramParser: TelegramInitDataParser,
    private val auth: AuthService,
) : AuthApiDelegate {
    private val log = autoLog()

    override suspend fun authTelegramWebAppPut(
        telegramInitDataMessage: TelegramInitDataMessage,
    ): ResponseEntity<AuthGrantMessage> {
        val initData = parseInitData(telegramInitDataMessage)
        val token = auth.authenticate(initData)
        val response = AuthGrantMessage(access = token.text)
        return ResponseEntity.ok(response)
    }

    private suspend fun parseInitData(telegramInitDataMessage: TelegramInitDataMessage) =
        try {
            telegramParser.parse(telegramInitDataMessage)
        } catch (error: GenericException) {
            log.warn("Telegram Init Data parsing failed: ${error.message}")
            throw AuthenticationException(
                "Corrupted ${telegramInitDataMessage.string.abbreviated()}: ${error.message}",
                error,
            )
        }
}
