package ru.ifmo.se.dating.authik.storage

import ru.ifmo.se.dating.security.auth.User

interface TelegramAccountStorage {
    suspend fun getOrInsert(telegramId: Long): User.Id
}
