package ru.ifmo.se.dating.people.logic.logging

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.people.logic.LocationService
import ru.ifmo.se.dating.people.model.Location

class LoggingLocationService(private val origin: LocationService) : LocationService {
    private val log = autoLog()

    override suspend fun create(draft: Location.Draft): Location =
        runCatching { origin.create(draft) }
            .onSuccess {
                log.info("Created a location '${it.name}' at ${it.coordinates} with id ${it.id}")
            }
            .onFailure { e -> log.error("Failed to create a location '${draft.name}'", e) }
            .getOrThrow()

    override suspend fun getById(id: Location.Id): Location? =
        runCatching { origin.getById(id) }
            .onSuccess { log.debug("Got location with id $id") }
            .onFailure { e -> log.warn("Failed to get location with id $id", e) }
            .getOrThrow()

    override suspend fun getAll(): Flow<Location> =
        runCatching { origin.getAll() }
            .onSuccess { log.debug("Got all locations") }
            .onFailure { e -> log.warn("Failed to get all locations: ${e.message}") }
            .getOrThrow()
}
