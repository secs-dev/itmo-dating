package ru.ifmo.se.dating.people.api.mapping

import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.model.generated.FacultyMessage

fun Faculty.toMessage() = FacultyMessage(
    id = id.number.toLong(),
    longName = longName,
)
