package ru.ifmo.se.dating.authik.logic

import ru.ifmo.se.dating.authik.model.generated.TelegramWebAppInitDataMessage
import ru.ifmo.se.dating.security.auth.AccessToken

interface AuthService {
    suspend fun authenticate(telegram: TelegramWebAppInitDataMessage): AccessToken
}
