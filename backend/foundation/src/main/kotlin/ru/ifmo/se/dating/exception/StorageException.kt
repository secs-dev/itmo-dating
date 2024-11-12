package ru.ifmo.se.dating.exception

open class StorageException(string: String, cause: Throwable? = null) :
    GenericException(string, cause)
