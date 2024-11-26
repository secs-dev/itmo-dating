package ru.ifmo.se.dating.storage.jooq

import org.jooq.exception.IntegrityConstraintViolationException
import ru.ifmo.se.dating.exception.StorageException
import ru.ifmo.se.dating.storage.exception.LinkViolationException
import ru.ifmo.se.dating.storage.exception.UniqueViolationException
import ru.ifmo.se.dating.storage.exception.UnknownException

private typealias Mapping =
    (IntegrityConstraintViolationException) -> StorageException

private val mapping = mapOf<_, Mapping>(
    "duplicate key value violates unique constraint" to {
        UniqueViolationException(it.message!!, it)
    },
    "violates foreign key constraint" to {
        LinkViolationException(it.message!!, it)
    },
)

fun IntegrityConstraintViolationException.toStorage(): StorageException {
    for (entry in mapping) {
        val (fragment, mapping) = entry
        if (this.message?.contains(fragment) == true) {
            return mapping(this)
        }
    }
    return UnknownException(this.message ?: "without message", this)
}
