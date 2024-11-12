package ru.ifmo.se.dating.storage

internal enum class FetchPolicy {
    SNAPSHOT,
    WRITE_LOCKED,
}
