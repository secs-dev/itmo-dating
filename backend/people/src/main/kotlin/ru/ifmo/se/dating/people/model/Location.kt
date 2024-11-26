package ru.ifmo.se.dating.people.model

import ru.ifmo.se.dating.validation.expectId
import ru.ifmo.se.dating.validation.expectMatches

data class Location(
    val id: Id,
    val name: String,
    val coordinates: Coordinates,
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
        val coordinates: Coordinates,
    ) {
        init {
            expectMatches("LocationName", name, nameRegex)
        }

        companion object {
            private val nameRegex = Regex("[A-Za-z ,.-]{3,128}")
        }
    }

    init {
        Draft(
            name = name,
            coordinates = coordinates,
        )
    }
}
