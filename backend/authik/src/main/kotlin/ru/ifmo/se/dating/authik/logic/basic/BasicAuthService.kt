package ru.ifmo.se.dating.authik.logic.basic

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.authik.logic.AuthService
import ru.ifmo.se.dating.authik.model.generated.TelegramWebAppInitDataMessage
import ru.ifmo.se.dating.authik.security.auth.JwtTokenIssuer
import ru.ifmo.se.dating.authik.storage.TelegramAccountStorage
import ru.ifmo.se.dating.security.auth.AccessToken

@Service
class BasicAuthService(
    private val telegramAccountStorage: TelegramAccountStorage,
    private val issuer: JwtTokenIssuer,
) : AuthService {
    override suspend fun authenticate(telegram: TelegramWebAppInitDataMessage): AccessToken {
        val userId = telegramAccountStorage.getOrInsert(telegram.user.id)
        return issuer.issue(AccessToken.Payload(userId))
    }
}
