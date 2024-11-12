package ru.ifmo.se.dating.exception

abstract class SecurityException(message: String, cause: Throwable? = null) :
    GenericException(message, cause)
