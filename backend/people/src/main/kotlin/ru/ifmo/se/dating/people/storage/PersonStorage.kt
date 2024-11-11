package ru.ifmo.se.dating.people.storage

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.model.Person

interface PersonStorage {
    suspend fun create(draft: Person.Draft): Person
    fun getAll(): Flow<Person>
}
