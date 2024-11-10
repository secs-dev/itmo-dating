package ru.ifmo.se.dating.people.model

import ru.ifmo.se.dating.validation.expectId
import ru.ifmo.se.dating.validation.expectMatches

data class Faculty(
    val id: Id,
    val shortName: String,
    val fullName: String
) {
    @JvmInline
    value class Id(private val number: Int) {
        init {
            expectId(number)
        }

        override fun toString(): String = number.toString()
    }

    data class Draft(
        val shortName: String,
        val fullName: String
    ) {
        init {
            validate(shortName, fullName)
        }
    }

    init {
        validate(shortName, fullName)
    }

    companion object {
        private val shortNameRegex = Regex("[A-Za-z]{2,16}")
        private val fullNameRegex = Regex("[A-Za-z ]{6,128}")

        fun validate(shortName: String, fullName: String) {
            expectMatches("Short name", shortName, shortNameRegex)
            expectMatches("Full name", fullName, fullNameRegex)
        }
    }
}
