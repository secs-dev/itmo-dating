package ru.ifmo.se.dating.people.logic

import ru.ifmo.se.dating.people.model.Area
import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.Topic
import java.time.LocalDate
import java.time.OffsetDateTime

data class PersonFilter(
    val firstName: Regex?,
    val lastName: Regex?,
    val height: IntRange,
    val birthday: ClosedRange<LocalDate>,
    val facultyId: Faculty.Id?,
    val updated: ClosedRange<OffsetDateTime>,
    val area: Area?,
    val picturesCount: ClosedRange<Int>,
    val zodiac: Person.Zodiac?,
    val topicIds: Set<Topic.Id>,
)
