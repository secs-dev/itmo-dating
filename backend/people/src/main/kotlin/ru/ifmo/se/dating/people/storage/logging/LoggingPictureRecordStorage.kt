package ru.ifmo.se.dating.people.storage.logging

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.people.model.Picture
import ru.ifmo.se.dating.people.storage.PictureRecordStorage

class LoggingPictureRecordStorage(private val origin: PictureRecordStorage) : PictureRecordStorage {
    private val log = autoLog()

    override suspend fun insert(): Picture =
        runCatching { origin.insert() }
            .onSuccess { log.info("Inserted a picture record with id ${it.id}") }
            .onFailure { e -> log.warn("Failed to insert a picture record", e) }
            .getOrThrow()

    override suspend fun setIsReferenced(id: Picture.Id, isReferenced: Boolean) =
        runCatching { origin.setIsReferenced(id, isReferenced) }
            .onSuccess {
                val status = if (isReferenced) {
                    "referenced"
                } else {
                    "unused"
                }
                log.info("Marked a picture record with id $id as $status")
            }
            .onFailure { e -> log.warn("Failed to change picture record with id $id", e) }
            .getOrThrow()

    override suspend fun delete(id: Picture.Id) =
        runCatching { origin.delete(id) }
            .onSuccess { log.info("Deleted a picture record with id $id") }
            .onFailure { e -> log.warn("Failed to delete a picture record with id $id", e) }
            .getOrThrow()

    override fun selectAbandoned(): Flow<Picture> =
        runCatching { origin.selectAbandoned() }
            .onSuccess { log.info("Retrieved abandoned pictures") }
            .onFailure { log.warn("Failed to select abandoned pictures") }
            .getOrThrow()
            .onEach {
                log.info("Considering an abandoned picture with id ${it.id}...")
            }
}
