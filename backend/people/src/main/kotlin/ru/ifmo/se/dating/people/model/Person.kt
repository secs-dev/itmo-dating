package ru.ifmo.se.dating.people.model

import ru.ifmo.se.dating.validation.expect
import ru.ifmo.se.dating.validation.expectId
import ru.ifmo.se.dating.validation.expectMatches
import java.time.LocalDate
import java.time.OffsetDateTime

data class Person(
    val id: Id,
    val firstName: Name,
    val lastName: Name,
    val height: Int,
    val birthday: LocalDate,
    val facultyId: Faculty.Id,
    val creationMoment: OffsetDateTime,
) {
    @JvmInline
    value class Id(private val number: Int) {
        init {
            expectId(number)
        }

        override fun toString(): String = number.toString()
    }

    @JvmInline
    value class Name(private val text: String) {
        init {
            expectMatches("First/Last Name", text, nameRegex)
        }

        override fun toString(): String = text

        companion object {
            private val nameRegex = Regex("[A-Za-z,.\'-]{2,32}")
        }
    }

    data class Draft(
        val firstName: Name,
        val lastName: Name,
        val height: Int,
        val birthday: LocalDate,
        val facultyId: Faculty.Id
    ) {
        init {
            validate(height, birthday)
        }
    }

    init {
        validate(height, birthday)
    }

    companion object {
        fun validate(height: Int, birthday: LocalDate) {
            expect(
                height in 50..280,
                "Height must be between 50 and 280, but got $height",
            )

            expect(
                birthday.isAfter(LocalDate.of(1990, 1, 1)),
                "Birthday must be after 1990-01-01, but got $birthday",
            )
        }
    }
}
