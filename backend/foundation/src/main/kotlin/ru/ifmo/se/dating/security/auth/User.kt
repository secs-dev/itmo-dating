package ru.ifmo.se.dating.security.auth

import ru.ifmo.se.dating.validation.expectId

object User {
    @JvmInline
    value class Id(val number: Int) {
        init {
            expectId(number)
        }
    }
}
