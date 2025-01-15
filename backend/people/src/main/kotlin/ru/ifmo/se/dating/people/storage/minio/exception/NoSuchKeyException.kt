package ru.ifmo.se.dating.people.storage.minio.exception

class NoSuchKeyException(message: String, cause: Throwable? = null) :
    MinioException(message, cause)
