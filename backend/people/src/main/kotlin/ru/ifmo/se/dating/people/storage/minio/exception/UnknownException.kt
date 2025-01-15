package ru.ifmo.se.dating.people.storage.minio.exception

class UnknownException(message: String, cause: Throwable? = null) :
    MinioException(message, cause)
