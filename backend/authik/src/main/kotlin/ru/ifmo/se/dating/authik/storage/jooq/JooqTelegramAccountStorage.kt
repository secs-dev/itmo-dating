package ru.ifmo.se.dating.authik.storage.jooq

import org.jooq.generated.tables.references.TELEGRAM_ACCOUNT
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.authik.storage.TelegramAccountStorage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqTelegramAccountStorage(
    private val database: JooqDatabase,
) : TelegramAccountStorage {
    override suspend fun select(telegramId: Long): User.Id? =
        database.maybe {
            selectFrom(TELEGRAM_ACCOUNT)
                .where(TELEGRAM_ACCOUNT.TELEGRAM_ID.eq(telegramId))
        }?.let { User.Id(it.accountId!!) }

    override suspend fun insert(telegramId: Long): User.Id =
        database.only {
            insertInto(TELEGRAM_ACCOUNT)
                .set(TELEGRAM_ACCOUNT.TELEGRAM_ID, telegramId)
                .returning()
        }.let { User.Id(it.accountId!!) }
}
