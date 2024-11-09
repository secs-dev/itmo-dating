package ru.ifmo.se.dating.exception

class InvalidValueException(string: String, cause: Throwable? = null) :
    GenericException(string, cause)
