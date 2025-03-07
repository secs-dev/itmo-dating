package ru.ifmo.se.dating.people.storage

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.pagging.Page
import ru.ifmo.se.dating.pagging.SortingKey
import ru.ifmo.se.dating.people.logic.PersonField
import ru.ifmo.se.dating.people.logic.PersonFilter
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.FetchPolicy

interface PersonStorage {
    suspend fun upsert(draft: Person.Draft): PersonVariant

    suspend fun setReadyMoment(id: User.Id)
    suspend fun resetReadyMoment(id: User.Id)

    suspend fun selectById(id: User.Id, policy: FetchPolicy = FetchPolicy.SNAPSHOT): PersonVariant?
    fun selectFilteredReady(
        page: Page,
        filter: PersonFilter,
        sortedBy: List<SortingKey<PersonField>>,
    ): Flow<Person>

    fun selectNotSentIds(limit: Int): Flow<User.Id>
    suspend fun setIsPublished(id: User.Id, isPublished: Boolean)
}
