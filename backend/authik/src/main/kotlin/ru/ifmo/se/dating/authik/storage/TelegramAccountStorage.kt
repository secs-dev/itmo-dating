package ru.ifmo.se.dating.authik.storage

import ru.ifmo.se.dating.security.auth.User

interface TelegramAccountStorage {
    suspend fun select(telegramId: Long): User.Id?
    suspend fun insert(telegramId: Long): User.Id
}
