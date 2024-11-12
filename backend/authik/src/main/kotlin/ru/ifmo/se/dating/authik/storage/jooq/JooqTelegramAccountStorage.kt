package ru.ifmo.se.dating.authik.storage.jooq

import org.jooq.generated.tables.references.TELEGRAM_ACCOUNT
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.authik.storage.TelegramAccountStorage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.TxEnv
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqTelegramAccountStorage(
    private val database: JooqDatabase,
    private val txEnv: TxEnv,
) : TelegramAccountStorage {
    override suspend fun getOrInsert(telegramId: Long): User.Id = txEnv.transactional {
        database.maybe {
            selectFrom(TELEGRAM_ACCOUNT)
                .where(TELEGRAM_ACCOUNT.TELEGRAM_ID.eq(telegramId))
        } ?: database.only {
            insertInto(TELEGRAM_ACCOUNT)
                .set(TELEGRAM_ACCOUNT.TELEGRAM_ID, telegramId)
                .returning()
        }
    }.let { User.Id(it.accountId!!) }
}
