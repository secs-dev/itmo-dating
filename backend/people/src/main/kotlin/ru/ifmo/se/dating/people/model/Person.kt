package ru.ifmo.se.dating.people.model

import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.validation.expect
import ru.ifmo.se.dating.validation.expectInRange
import ru.ifmo.se.dating.validation.expectMatches
import java.time.LocalDate

sealed class PersonVariant {
    abstract val id: User.Id
    abstract val pictureIds: List<Picture.Id>
    abstract val version: Person.Version
}

data class Person(
    override val id: User.Id,
    val firstName: Name,
    val lastName: Name,
    val height: Int,
    val birthday: LocalDate,
    val facultyId: Faculty.Id,
    val locationId: Location.Id,
    override val pictureIds: List<Picture.Id>,
    override val version: Version,
    val isPublished: Boolean,
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

    @JvmInline
    value class Version(val number: Int) {
        init {
            expect(0 <= number, "Version must be non negative")
        }
    }

    data class Draft(
        override val id: User.Id,
        val firstName: Name? = null,
        val lastName: Name? = null,
        val height: Int? = null,
        val birthday: LocalDate? = null,
        val facultyId: Faculty.Id? = null,
        val locationId: Location.Id? = null,
        override val pictureIds: List<Picture.Id> = emptyList(),
        override val version: Version = Version(0),
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
