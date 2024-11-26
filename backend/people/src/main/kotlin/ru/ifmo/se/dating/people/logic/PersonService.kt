package ru.ifmo.se.dating.people.logic

import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.security.auth.User

interface PersonService {
    suspend fun edit(draft: Person.Draft)
    suspend fun save(expected: Person.Draft)
    suspend fun getById(id: User.Id): PersonVariant?
}
