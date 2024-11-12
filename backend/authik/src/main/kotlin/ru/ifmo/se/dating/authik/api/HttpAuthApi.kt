package ru.ifmo.se.dating.authik.api

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.authik.api.generated.AuthApiDelegate
import ru.ifmo.se.dating.authik.logic.AuthService
import ru.ifmo.se.dating.authik.model.generated.AuthGrantMessage
import ru.ifmo.se.dating.authik.model.generated.TelegramInitDataMessage
import ru.ifmo.se.dating.authik.telegram.InitDataParser
import ru.ifmo.se.dating.exception.AuthenticationException
import ru.ifmo.se.dating.exception.GenericException
import ru.ifmo.se.dating.text.abbreviated

@Controller
class HttpAuthApi(
    private val telegramParser: InitDataParser,
    private val auth: AuthService,
) : AuthApiDelegate {
    override suspend fun authTelegramWebAppPut(
        telegramInitDataMessage: TelegramInitDataMessage,
    ): ResponseEntity<AuthGrantMessage> {
        val initData = try {
            telegramParser.parse(telegramInitDataMessage)
        } catch (error: GenericException) {
            throw AuthenticationException(
                "Corrupted ${telegramInitDataMessage.string.abbreviated()}: ${error.message}",
                error,
            )
        }
        val token = auth.authenticate(initData)
        val response = AuthGrantMessage(access = token.text)
        return ResponseEntity.ok(response)
    }
}
