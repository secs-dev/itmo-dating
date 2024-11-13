package ru.ifmo.se.dating.matchmaker.exception

import ru.ifmo.se.dating.exception.GenericException

sealed class DomainException(message: String, cause: Throwable? = null) :
    GenericException(message, cause)
