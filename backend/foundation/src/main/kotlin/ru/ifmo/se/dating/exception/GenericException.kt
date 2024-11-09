package ru.ifmo.se.dating.exception

open class GenericException(message: String, cause: Throwable? = null) :
    Exception(message, cause)
