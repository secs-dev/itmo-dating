package ru.ifmo.se.dating.people.model

import ru.ifmo.se.dating.validation.expectId
import ru.ifmo.se.dating.validation.expectMatches

data class Topic(
    val id: Id,
    val name: String,
) {
    @JvmInline
    value class Id(val number: Int) {
        init {
            expectId(number)
        }

        override fun toString(): String = number.toString()
    }

    data class Draft(
        val name: String,
    ) {
        init {
            validate(name)
        }
    }

    init {
        validate(name)
    }

    companion object {
        private val nameRegex = Regex("^[A-Za-z]{3,32}$")

        fun validate(name: String) {
            expectMatches("Topic name", name, nameRegex)
        }
    }
}
