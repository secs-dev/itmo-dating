package ru.ifmo.se.dating.logic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.logging.Log

class LoggingTransactionalOutbox<E, Id>(
    private val origin: TransactionalOutbox<E, Id>
) : TransactionalOutbox<E, Id> by origin {
    private val log = Log.forClass(origin.javaClass)

    override suspend fun publishable(): Flow<Id> =
        runCatching { origin.publishable() }
            .onSuccess { log.info("Retrieved publishable events") }
            .onFailure { log.warn("Failed to retrieve publishable events") }
            .getOrThrow()

    override suspend fun acquireById(id: Id): E =
        runCatching { origin.acquireById(id) }
            .onSuccess { event ->
                val status = if (isPublished(event)) {
                    "a published"
                } else {
                    "an unpublished"
                }
                log.info("Acquired $status event with id $id")
            }
            .onFailure { log.warn("Failed to acquire an event with id $id") }
            .getOrThrow()

    override suspend fun isPublished(event: E): Boolean =
        origin.isPublished(event)

    override suspend fun doProcess(event: E) =
        runCatching { origin.doProcess(event) }
            .onSuccess { log.info("Executed an event processing") }
            .onFailure { e -> log.warn("Failed to process an event: ${e.message}") }
            .getOrThrow()

    override suspend fun markPublished(event: E) =
        runCatching { origin.markPublished(event) }
            .onSuccess { log.info("Marked an event as published") }
            .onFailure { e -> log.warn("Failed to mark an event as published: ${e.message}") }
            .getOrThrow()

    override suspend fun process(id: Id) {
        log.info("Processing an event with id $id...")
        runCatching { super.process(id) }
            .onSuccess { log.info("An event with id $id was processed") }
            .onFailure { log.warn("Failed to process an event with id $id") }
            .getOrThrow()
    }

    override suspend fun recover() {
        log.info("Starting the recovery...")
        runCatching { super.recover() }
            .onSuccess { log.info("Recovery was completed.") }
            .onFailure { e -> log.warn("Recovery was failed", e) }
            .getOrThrow()
    }
}
