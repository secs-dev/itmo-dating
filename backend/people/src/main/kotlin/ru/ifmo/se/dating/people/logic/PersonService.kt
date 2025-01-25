package ru.ifmo.se.dating.people.logic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.pagging.Page
import ru.ifmo.se.dating.pagging.SortingKey
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.security.auth.User

interface PersonService {
    suspend fun edit(draft: Person.Draft)
    suspend fun save(expected: Person.Draft)
    suspend fun getById(id: User.Id): PersonVariant?
    suspend fun delete(id: User.Id)

    suspend fun getFiltered(
        page: Page,
        filter: PersonFilter,
        sortedBy: List<SortingKey<PersonField>>,
    ): Flow<Person>
}
