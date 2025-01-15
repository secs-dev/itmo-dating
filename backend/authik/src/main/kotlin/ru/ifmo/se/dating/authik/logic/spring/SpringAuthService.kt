package ru.ifmo.se.dating.authik.logic.spring

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.authik.logic.AuthService
import ru.ifmo.se.dating.authik.logic.basic.BasicAuthService
import ru.ifmo.se.dating.authik.logic.logging.LoggingAuthService
import ru.ifmo.se.dating.authik.security.auth.TokenIssuer
import ru.ifmo.se.dating.authik.storage.TelegramAccountStorage
import ru.ifmo.se.dating.storage.TxEnv

@Service
class SpringAuthService(
    telegramAccountStorage: TelegramAccountStorage,
    issuer: TokenIssuer,
    txEnv: TxEnv,
) : AuthService by
LoggingAuthService(
    BasicAuthService(
        telegramAccountStorage = telegramAccountStorage,
        issuer = issuer,
        txEnv = txEnv,
    )
)
