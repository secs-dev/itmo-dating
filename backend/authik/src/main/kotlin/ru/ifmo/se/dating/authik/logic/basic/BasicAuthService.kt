package ru.ifmo.se.dating.authik.logic.basic

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.authik.logic.AuthService
import ru.ifmo.se.dating.authik.model.generated.TelegramWebAppInitDataMessage
import ru.ifmo.se.dating.authik.security.auth.JwtTokenIssuer
import ru.ifmo.se.dating.authik.storage.TelegramAccountStorage
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.security.auth.AccessToken
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.TxEnv

@Service
class BasicAuthService(
    private val telegramAccountStorage: TelegramAccountStorage,
    private val issuer: JwtTokenIssuer,
    private val txEnv: TxEnv,
) : AuthService {
    private val log = autoLog()

    init {
        val adamToken = issuer.issue(AccessToken.Payload(User.Id(ADAM_ID)))
        log.info("Issued adam token: '${adamToken.text}'")
    }

    override suspend fun authenticate(telegram: TelegramWebAppInitDataMessage): AccessToken =
        txEnv.transactional {
            val userId = telegramAccountStorage.select(telegram.user.id)
                ?: telegramAccountStorage.insert(telegram.user.id)
            issuer.issue(AccessToken.Payload(userId))
        }

    companion object {
        const val ADAM_ID = 666_666
    }
}
