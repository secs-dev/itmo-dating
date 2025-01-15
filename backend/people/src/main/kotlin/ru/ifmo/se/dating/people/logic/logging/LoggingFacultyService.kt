package ru.ifmo.se.dating.people.logic.logging

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.people.logic.FacultyService
import ru.ifmo.se.dating.people.model.Faculty

class LoggingFacultyService(private val origin: FacultyService) : FacultyService {
    private val log = autoLog()

    override suspend fun create(draft: Faculty.Draft): Faculty =
        runCatching { origin.create(draft) }
            .onSuccess { log.info("Create a faculty '${it.longName}' with id ${it.id}") }
            .onFailure { e -> log.warn("Failed to create a faculty ${draft.longName}", e) }
            .getOrThrow()

    override suspend fun getById(id: Faculty.Id): Faculty? =
        runCatching { origin.getById(id) }
            .onSuccess { log.debug("Got faculty with id $id") }
            .onFailure { e -> log.warn("Failed to get faculty with id $id", e) }
            .getOrThrow()

    override fun getAll(): Flow<Faculty> =
        runCatching { origin.getAll() }
            .onSuccess { log.debug("Got all faculties") }
            .onFailure { e -> log.warn("Failed to get all faculties: ${e.message}") }
            .getOrThrow()
}