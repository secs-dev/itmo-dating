package ru.ifmo.se.dating.people.storage.jooq.mapping

import org.jooq.generated.tables.records.PersonRecord
import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.model.Location
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.security.auth.User

fun PersonRecord.toModel(): PersonVariant =
    if (readyMoment != null) {
        Person(
            id = User.Id(accountId),
            firstName = Person.Name(firstName!!),
            lastName = Person.Name(lastName!!),
            height = height!!,
            birthday = birthday!!,
            facultyId = Faculty.Id(facultyId!!),
            locationId = Location.Id(locationId!!),
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
            version = Person.Version(version!!),
        )
    }
