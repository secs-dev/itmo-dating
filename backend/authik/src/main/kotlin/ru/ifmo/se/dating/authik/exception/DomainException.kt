package ru.ifmo.se.dating.authik.exception

import ru.ifmo.se.dating.exception.GenericException

sealed class DomainException(message: String, cause: Throwable? = null) :
    GenericException(message, cause)
