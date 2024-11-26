package ru.ifmo.se.dating.people.model

import ru.ifmo.se.dating.validation.expectId
import ru.ifmo.se.dating.validation.expectMatches

data class Faculty(
    val id: Id,
    val longName: String,
) {
    @JvmInline
    value class Id(val number: Int) {
        init {
            expectId(number)
        }

        override fun toString(): String = number.toString()
    }

    data class Draft(
        val longName: String,
    ) {
        init {
            expectMatches("FacultyLongName", longName, longNameRegex)
        }

        companion object {
            private val longNameRegex = Regex("[A-Za-z ]{4,64}")
        }
    }

    init {
        Draft(
            longName = longName,
        )
    }
}
