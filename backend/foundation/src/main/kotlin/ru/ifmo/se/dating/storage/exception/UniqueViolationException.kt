package ru.ifmo.se.dating.storage.exception

import ru.ifmo.se.dating.exception.StorageException

class UniqueViolationException(message: String, cause: Throwable? = null) :
    StorageException(message, cause)
