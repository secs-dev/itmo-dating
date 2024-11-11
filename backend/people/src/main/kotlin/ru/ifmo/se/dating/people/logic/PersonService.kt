package ru.ifmo.se.dating.people.logic

import ru.ifmo.se.dating.people.model.Person

interface PersonService {
    suspend fun create(draft: Person.Draft): Person
    suspend fun getById(id: Person.Id): Person?
}
