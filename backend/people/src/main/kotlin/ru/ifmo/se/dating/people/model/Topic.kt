package ru.ifmo.se.dating.people.model

import ru.ifmo.se.dating.validation.expectId
import ru.ifmo.se.dating.validation.expectMatches

data class Topic(
    val id: Id,
    val name: String,
    val color: Color,
) {
    @JvmInline
    value class Id(val number: Int) {
        init {
            expectId(number)
        }

        override fun toString(): String = number.toString()
    }

    @JvmInline
    value class Color(val hex: String) {
        init {
            expectMatches("Hex", hex, regex)
        }

        override fun toString(): String = hex

        companion object {
            private val regex = Regex("#[A-F0-9]{6}")
        }
    }

    init {
        expectMatches("Name", name, nameRegex)
    }

    companion object {
        private val nameRegex = Regex("[A-Z][a-z]{3,32}")
    }
}
