package ru.ifmo.se.dating.people.model

import ru.ifmo.se.dating.people.model.Person.Interest
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.validation.expect
import ru.ifmo.se.dating.validation.expectInRange
import ru.ifmo.se.dating.validation.expectMatches
import java.time.LocalDate
import java.time.OffsetDateTime

sealed class PersonVariant {
    abstract val id: User.Id
    abstract val interests: Set<Interest>
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
    val location: Location,
    override val interests: Set<Interest>,
    override val pictureIds: List<Picture.Id>,
    val updateMoment: OffsetDateTime,
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

    data class Interest(
        val topicId: Topic.Id,
        val degree: Int,
    ) {
        init {
            expectInRange("Level", degree, degreeRange)
        }

        companion object {
            private val degreeRange = 1..5
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
        override val interests: Set<Interest> = emptySet(),
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

    enum class Zodiac {
        ARIES,
        TAURUS,
        GEMINI,
        CANCER,
        LEO,
        VIRGO,
        LIBRA,
        SCORPIO,
        SAGITTARIUS,
        CAPRICORN,
        AQUARIUS,
        PISCES,
    }

    @Suppress("MagicNumber")
    val zodiac: Zodiac
        get() {
            val month = birthday.monthValue
            val day = birthday.dayOfMonth
            return when {
                month == 3 && day >= 21 || month == 4 && day <= 19 -> Zodiac.ARIES
                month == 4 || month == 5 && day <= 20 -> Zodiac.TAURUS
                month == 5 || month == 6 && day <= 20 -> Zodiac.GEMINI
                month == 6 || month == 7 && day <= 22 -> Zodiac.CANCER
                month == 7 || month == 8 && day <= 22 -> Zodiac.LEO
                month == 8 || month == 9 && day <= 22 -> Zodiac.VIRGO
                month == 9 || month == 10 && day <= 22 -> Zodiac.LIBRA
                month == 10 || month == 11 && day <= 21 -> Zodiac.SCORPIO
                month == 11 || month == 12 && day <= 21 -> Zodiac.SAGITTARIUS
                month == 12 || month == 1 && day <= 19 -> Zodiac.CAPRICORN
                month == 1 || month == 2 && day <= 18 -> Zodiac.AQUARIUS
                month == 2 || month == 3 -> Zodiac.PISCES
                else -> throw AssertionError("Can't happen")
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
            locationId = location.id,
        )
    }
}
