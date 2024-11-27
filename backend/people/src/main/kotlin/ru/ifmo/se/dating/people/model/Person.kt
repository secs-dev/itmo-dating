package ru.ifmo.se.dating.people.model

import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.validation.expectInRange
import ru.ifmo.se.dating.validation.expectMatches
import java.time.LocalDate

sealed class PersonVariant

data class Person(
    val id: User.Id,
    val firstName: Name,
    val lastName: Name,
    val height: Int,
    val birthday: LocalDate,
    val facultyId: Faculty.Id,
    val locationId: Location.Id
) : PersonVariant() {
    @JvmInline
    value class Name(val text: String) {
        init {
            expectMatches("PersonName", text, regex)
        }

        companion object {
            private val regex = Regex("[A-Za-z,.'-]{2,32}")
        }
    }

    data class Draft(
        val id: User.Id,
        val firstName: Name? = null,
        val lastName: Name? = null,
        val height: Int? = null,
        val birthday: LocalDate? = null,
        val facultyId: Faculty.Id? = null,
        val locationId: Location.Id? = null,
    ) : PersonVariant() {
        init {
            height?.let { expectInRange("Height", it, heightRange) }
            birthday?.let { expectInRange("Birthday", it, birthdayRange) }
        }

        companion object {
            private val heightRange = 50..280
            private val birthdayRange = LocalDate.of(1990, 1, 1)..LocalDate.of(2032, 1, 1)
        }
    }

    enum class Field {
        FIRST_NAME,
        LAST_NAME,
        HEIGHT,
        BIRTHDAY,
        UPDATED,
    }

    init {
        Draft(
            id = id,
            firstName = firstName,
            lastName = lastName,
            height = height,
            birthday = birthday,
            facultyId = facultyId,
            locationId = locationId,
        )
    }
}
