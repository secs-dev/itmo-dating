package ru.ifmo.se.dating.matchmaker.model

import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.validation.expect

data class PersonUpdate(
    val id: User.Id,
    val version: Version,
) {
    @JvmInline
    value class Version(val number: Int) : Comparable<Version> {
        init {
            expect(0 <= number, "Version must be non negative")
        }

        override fun compareTo(other: Version): Int =
            this.number.compareTo(other.number)
    }
}
