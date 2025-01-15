package ru.ifmo.se.dating.people.model

import ru.ifmo.se.dating.validation.expectId

data class Picture(val id: Id, val isReferenced: Boolean) {
    @JvmInline
    value class Id(val number: Int) {
        init {
            expectId(number)
        }

        override fun toString(): String = number.toString()
    }

    data class Content(val bytes: ByteArray) {
        override fun equals(other: Any?): Boolean =
            (this === other) ||
                    (javaClass == other?.javaClass) &&
                    bytes.contentEquals((other as Content).bytes)

        override fun hashCode(): Int =
            bytes.contentHashCode()
    }
}
