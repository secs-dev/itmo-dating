package ru.ifmo.se.dating.people.logic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.pagging.Page
import ru.ifmo.se.dating.people.model.*
import ru.ifmo.se.dating.security.auth.User
import java.time.LocalDate
import java.time.OffsetDateTime

interface PersonService {
    suspend fun edit(draft: Person.Draft)
    suspend fun save(expected: Person.Draft)
    suspend fun getById(id: User.Id): PersonVariant?
    suspend fun delete(id: User.Id)

    fun getFiltered(page: Page, filter: Filter): Flow<Person>

    data class Filter(
        val firstName: Regex?,
        val lastName: Regex?,
        val height: IntRange?,
        val birthday: ClosedRange<LocalDate>,
        val facultyId: Faculty.Id?,
        val updated: ClosedRange<OffsetDateTime>,
        val area: Area?,
        val picturesCount: ClosedRange<Int>,
        val zodiac: Person.Zodiac?,
        val topicIds: Set<Topic.Id>,
    )
}
