package ru.ifmo.se.dating.people.model

import ru.ifmo.se.dating.validation.expect
import ru.ifmo.se.dating.validation.expectId
import ru.ifmo.se.dating.validation.expectMatches
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Person(
    val id: Id,
    val firstName: Name,
    val lastName: Name,
    val height: Int,
    val birthday: LocalDate,
    val facultyId: Faculty.Id,
    val interests: Set<Interest>,
) {
    @JvmInline
    value class Id(val number: Int) {
        init {
            expectId(number)
        }

        override fun toString(): String = number.toString()
    }

    @JvmInline
    value class Name(val text: String) {
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
            expect(
                rank in rankRange,
                "Interest rank must be in [${rankRange.first}, ${rankRange.last}], got $rank",
            )
        }

        companion object {
            private val rankRange = 1..5
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
        private val heightRange = 50..280
        private val birthdayMin = LocalDate.of(1990, 1, 1)
        private val interestsCountRange = 0..8

        fun validate(
            height: Int,
            birthday: LocalDate,
            interests: Set<Interest>,
        ) {
            expect(height in heightRange) {
                append("Height must be between ${heightRange.first} and ${heightRange.last}, ")
                append("but got $height")
            }

            expect(birthday.isAfter(birthdayMin)) {
                append("Birthday must be after ")
                append(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(birthdayMin))
                append(", but got $birthday")
            }

            expect(interests.size in interestsCountRange) {
                append("Person must have no more than ${interestsCountRange.last} ")
                append("interests, got ${interests.size}")
            }
        }
    }
}
