package ru.ifmo.se.dating.matchmaker.storage

import ru.ifmo.se.dating.matchmaker.model.PersonUpdate
import ru.ifmo.se.dating.security.auth.User

interface PersonStorage {
    suspend fun selectById(id: User.Id): PersonUpdate?
    suspend fun upsert(person: PersonUpdate)
}
