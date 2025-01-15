package ru.ifmo.se.dating.people.storage.logging

import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.people.model.Picture
import ru.ifmo.se.dating.people.storage.PictureContentStorage

class LoggingPictureContentStorage(private val origin: PictureContentStorage) : PictureContentStorage {
    private val log = autoLog()

    override suspend fun upload(id: Picture.Id, content: Picture.Content) =
        runCatching { origin.upload(id, content) }
            .onSuccess { log.info("Uploaded a picture with id $id") }
            .onFailure { e -> log.warn("Failed to upload a picture with id $id", e) }
            .getOrThrow()

    override suspend fun download(id: Picture.Id): Picture.Content =
        runCatching { origin.download(id) }
            .onSuccess { log.info("Downloaded a picture with id $id") }
            .onFailure { e -> log.warn("Failed to download a picture with id $id", e) }
            .getOrThrow()

    override suspend fun remove(id: Picture.Id) =
        runCatching { origin.remove(id) }
            .onSuccess { log.info("Removed a picture with id $id") }
            .onFailure { e -> log.warn("Failed to remove a picture with id $id", e) }
            .getOrThrow()
}
