package ru.ifmo.se.dating.people.storage.jooq.mapping

import org.jooq.generated.tables.records.PersonInterestRecord
import org.jooq.generated.tables.records.PersonRecord
import ru.ifmo.se.dating.people.model.*
import ru.ifmo.se.dating.security.auth.User

val PersonRecord.isReady get() = readyMoment != null

fun PersonRecord.toModel(
    location: Location?,
    interests: Set<Person.Interest>,
    pictureIds: List<Picture.Id>,
): PersonVariant =
    if (isReady) {
        Person(
            id = User.Id(accountId),
            firstName = Person.Name(firstName!!),
            lastName = Person.Name(lastName!!),
            height = height!!,
            birthday = birthday!!,
            interests = interests,
            facultyId = Faculty.Id(facultyId!!),
            location = location!!,
            pictureIds = pictureIds,
            updateMoment = updateMoment,
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
            interests = interests,
            facultyId = facultyId?.let { Faculty.Id(it) },
            locationId = locationId?.let { Location.Id(it) },
            pictureIds = pictureIds,
            version = Person.Version(version!!),
        )
    }

fun PersonInterestRecord.toModel(): Person.Interest =
    Person.Interest(
        topicId = Topic.Id(topicId),
        degree = degree,
    )
