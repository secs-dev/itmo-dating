package ru.ifmo.se.dating.exception

class AuthorizationException(message: String, cause: Throwable? = null) :
    SecurityException(message, cause)
