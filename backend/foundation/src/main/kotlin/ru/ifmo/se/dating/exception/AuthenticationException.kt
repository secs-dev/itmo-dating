package ru.ifmo.se.dating.exception

class AuthenticationException(message: String, cause: Throwable? = null) :
    SecurityException(message, cause)
