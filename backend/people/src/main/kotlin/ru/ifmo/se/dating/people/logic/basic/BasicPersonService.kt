package ru.ifmo.se.dating.people.logic.basic

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.people.logic.PersonService
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.storage.PersonStorage

@Service
class BasicPersonService(private val storage: PersonStorage) : PersonService {
    override suspend fun create(draft: Person.Draft): Person =
        storage.create(draft)

    override suspend fun getById(id: Person.Id): Person? =
        storage.getAll().filter { it.id == id }.firstOrNull()
}
