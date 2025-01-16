package ru.ifmo.se.dating.people.storage.jooq.mapping

import org.jooq.generated.tables.records.PersonRecord
import ru.ifmo.se.dating.people.model.*
import ru.ifmo.se.dating.security.auth.User

fun PersonRecord.toModel(pictureIds: List<Picture.Id>): PersonVariant =
    if (readyMoment != null) {
        Person(
            id = User.Id(accountId),
            firstName = Person.Name(firstName!!),
            lastName = Person.Name(lastName!!),
            height = height!!,
            birthday = birthday!!,
            facultyId = Faculty.Id(facultyId!!),
            locationId = Location.Id(locationId!!),
            pictureIds = pictureIds,
            version = Person.Version(version!!),
            isPublished = isPublished!!,
        )
    } else {
        Person.Draft(
            id = User.Id(accountId),
            firstName = firstName?.let { Person.Name(it) },
            lastName = lastName?.let { Person.Name(it) },
            height = height,
            birthday = birthday,
            facultyId = facultyId?.let { Faculty.Id(it) },
            locationId = locationId?.let { Location.Id(it) },
            pictureIds = pictureIds,
            version = Person.Version(version!!),
        )
    }
