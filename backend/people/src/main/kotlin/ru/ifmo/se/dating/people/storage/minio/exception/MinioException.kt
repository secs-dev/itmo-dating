package ru.ifmo.se.dating.people.storage.minio.exception

import ru.ifmo.se.dating.exception.GenericException

sealed class MinioException(message: String, cause: Throwable? = null) :
    GenericException(message, cause)
