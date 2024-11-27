package ru.ifmo.se.dating.people.storage

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.security.auth.User

interface PersonStorage {
    suspend fun upsert(draft: Person.Draft)
    suspend fun setReadyMoment(id: User.Id)
    suspend fun selectById(id: User.Id): PersonVariant?
    fun selectAllReady(): Flow<Person>
}
