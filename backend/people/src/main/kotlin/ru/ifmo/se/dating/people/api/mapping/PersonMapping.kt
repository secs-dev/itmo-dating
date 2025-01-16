package ru.ifmo.se.dating.people.api.mapping

import ru.ifmo.se.dating.people.model.*
import ru.ifmo.se.dating.people.model.generated.*
import ru.ifmo.se.dating.security.auth.User

fun PersonPatchMessage.toModel(id: Int) = Person.Draft(
    id = User.Id(id),
    firstName = firstName?.let { Person.Name(it) },
    lastName = lastName?.let { Person.Name(it) },
    height = height,
    birthday = birthday,
    interests = emptySet(),
    facultyId = facultyId?.let { Faculty.Id(it.toInt()) },
    locationId = locationId?.let { Location.Id(it.toInt()) },
)

fun PersonVariant.toMessage() = when (this) {
    is Person.Draft -> toMessage()
    is Person -> toMessage()
}

fun Person.toMessage() = PersonMessage(
    status = PersonStatusMessage.ready,
    userId = id.number.toLong(),
    firstName = firstName.text,
    lastName = lastName.text,
    height = height,
    birthday = birthday,
    facultyId = facultyId.number.toLong(),
    locationId = locationId.number.toLong(),
    interests = interests.map { it.toMessage() }.toSet(),
    zodiac = zodiac.toMessage(),
    pictures = pictureIds.map { PictureMessage(id = it.number.toLong()) }.toSet()
)

fun Person.Draft.toMessage() = PersonDraftMessage(
    status = PersonStatusMessage.draft,
    userId = id.number.toLong(),
    firstName = firstName?.text,
    lastName = lastName?.text,
    height = height,
    birthday = birthday,
    facultyId = facultyId?.number?.toLong(),
    locationId = locationId?.number?.toLong(),
    interests = interests.map { it.toMessage() }.toSet(),
    pictures = pictureIds.map { PictureMessage(id = it.number.toLong()) }.toSet()
)

fun InterestMessage.toModel(): Person.Interest =
    Person.Interest(
        topicId = Topic.Id(topicId.toInt()),
        degree = level.value.toInt(),
    )

fun Person.Interest.toMessage(): InterestMessage =
    InterestMessage(
        topicId = topicId.number.toLong(),
        level = InterestLevelMessage.forValue(degree.toString()),
    )

fun Person.Zodiac.toMessage(): ZodiacSignMessage = when (this) {
    Person.Zodiac.ARIES -> ZodiacSignMessage.aries
    Person.Zodiac.TAURUS -> ZodiacSignMessage.taurus
    Person.Zodiac.GEMINI -> ZodiacSignMessage.gemini
    Person.Zodiac.CANCER -> ZodiacSignMessage.cancer
    Person.Zodiac.LEO -> ZodiacSignMessage.leo
    Person.Zodiac.VIRGO -> ZodiacSignMessage.virgo
    Person.Zodiac.LIBRA -> ZodiacSignMessage.libra
    Person.Zodiac.SCORPIO -> ZodiacSignMessage.scorpio
    Person.Zodiac.SAGITTARIUS -> ZodiacSignMessage.sagittarius
    Person.Zodiac.CAPRICORN -> ZodiacSignMessage.capricorn
    Person.Zodiac.AQUARIUS -> ZodiacSignMessage.aquarius
    Person.Zodiac.PISCES -> ZodiacSignMessage.pisces
}
