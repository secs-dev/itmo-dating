package ru.ifmo.se.dating.storage.jooq.exception

import ru.ifmo.se.dating.exception.StorageException

class UnknownException(message: String, cause: Throwable? = null) :
    StorageException(message, cause)