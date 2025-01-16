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
    interests = interests?.map { it.toModel() }?.toSet() ?: emptySet(),
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
    zodiac = ZodiacSignMessage.leo,
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
