package ru.ifmo.se.dating.people.logic.logging

import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.people.logic.PictureService
import ru.ifmo.se.dating.people.model.Picture

class LoggingPictureService(private val origin: PictureService) : PictureService {
    private val log = autoLog()

    override suspend fun getById(id: Picture.Id): Picture.Content =
        runCatching { origin.getById(id) }
            .onSuccess { log.info("Retrieved picture with id $id") }
            .onFailure { e -> log.warn("Failed to retrieve picture with id $id: ${e.message}") }
            .getOrThrow()

    override suspend fun save(content: Picture.Content): Picture.Id =
        runCatching { origin.save(content) }
            .onSuccess { pictureId -> log.info("Saved picture with id $pictureId") }
            .onFailure { e -> log.warn("Failed to save picture: ${e.message}") }
            .getOrThrow()

    override suspend fun remove(id: Picture.Id) =
        runCatching { origin.remove(id) }
            .onSuccess { log.info("Removed picture with id $id") }
            .onFailure { e -> log.warn("Failed to remove picture with id $id: ${e.message}") }
            .getOrThrow()

    override suspend fun recover() {
        log.info("Starting pictures recovery...")
        runCatching { origin.recover() }
            .onSuccess { log.info("Successfully cleaned abandoned pictures") }
            .onFailure { e -> log.warn("Failed to clean some abandoned pictures", e) }
            .getOrThrow()
    }
}
