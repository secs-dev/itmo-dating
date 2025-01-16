package ru.ifmo.se.dating.people.logic.logging

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.pagging.Page
import ru.ifmo.se.dating.people.logic.PersonService
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.security.auth.User

class LoggingPersonService(private val origin: PersonService) : PersonService {
    private val log = autoLog()

    override suspend fun edit(draft: Person.Draft) =
        runCatching { origin.edit(draft) }
            .onSuccess { log.info("Edited person with id ${draft.id}") }
            .onFailure { e -> log.warn("Failed to edit a person with id ${draft.id}", e) }
            .getOrThrow()

    override suspend fun save(expected: Person.Draft) =
        runCatching { origin.save(expected) }
            .onSuccess { log.info("Saved person with id ${expected.id}") }
            .onFailure { e -> log.warn("Failed to save a person with id ${expected.id}", e) }
            .getOrThrow()

    override suspend fun getById(id: User.Id): PersonVariant? =
        runCatching { origin.getById(id) }
            .onSuccess { log.debug("Retrieved person with id $id") }
            .onFailure { e -> log.warn("Failed to retrieve a person with id $id", e) }
            .getOrThrow()

    override suspend fun delete(id: User.Id) =
        runCatching { origin.delete(id) }
            .onSuccess { log.info("Deleted person with id $id") }
            .onFailure { e -> log.warn("Failed to delete a person with id $id", e) }
            .getOrThrow()

    override fun getFiltered(page: Page, filter: PersonService.Filter): Flow<Person> =
        runCatching { origin.getFiltered(page, filter) }
            .onSuccess {
                log.debug("Retrieved people at offset ${page.offset} and limit ${page.limit}")
            }
            .onFailure { e ->
                buildString {
                    append("Failed to retrieve people ")
                    append("at offset ${page.offset} and limit ${page.limit}")
                }.let { log.warn(it, e) }
            }
            .getOrThrow()
}
