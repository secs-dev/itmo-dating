package ru.ifmo.se.dating.exception

class ConflictException(message: String, cause: Exception? = null) :
    GenericException(message, cause)
