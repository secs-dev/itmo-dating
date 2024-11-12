package ru.ifmo.se.dating.exception

class NotFoundException(message: String, cause: Throwable? = null) :
    GenericException("Not found: $message", cause)

fun <T> T?.orThrowNotFound(message: String): T =
    this ?: throw NotFoundException(message)
