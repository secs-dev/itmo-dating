package ru.ifmo.se.dating.people.api.mapping

import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.model.Location
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.people.model.generated.*
import ru.ifmo.se.dating.security.auth.User

fun PersonPatchMessage.toModel(id: Int) = Person.Draft(
    id = User.Id(id),
    firstName = firstName?.let { Person.Name(it) },
    lastName = lastName?.let { Person.Name(it) },
    height = height,
    birthday = birthday,
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
    interests = emptySet(),
    zodiac = ZodiacSignMessage.leo,
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
    interests = emptySet(),
)
