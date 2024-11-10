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
    val interests: Set<Interest>,
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

    data class Interest(
        val topicId: Topic.Id,
        val rank: Int,
    ) {
        init {
            expect(rank in 1..5, "Interest rank must be in [1, 5], got $rank")
        }
    }

    data class Draft(
        val firstName: Name,
        val lastName: Name,
        val height: Int,
        val birthday: LocalDate,
        val facultyId: Faculty.Id,
        val interests: Set<Interest>,
    ) {
        init {
            validate(height, birthday, interests)
        }
    }

    init {
        validate(height, birthday, interests)
    }

    companion object {
        fun validate(
            height: Int,
            birthday: LocalDate,
            interests: Set<Interest>,
        ) {
            expect(
                height in 50..280,
                "Height must be between 50 and 280, but got $height",
            )

            expect(
                birthday.isAfter(LocalDate.of(1990, 1, 1)),
                "Birthday must be after 1990-01-01, but got $birthday",
            )

            expect(
                interests.size in 0..8,
                "Person must have no more than 8 interests, got ${interests.size}",
            )
        }
    }
}
