package ru.ifmo.se.dating.people.logic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.pagging.Page
import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.security.auth.User
import java.time.LocalDate

interface PersonService {
    suspend fun edit(draft: Person.Draft)
    suspend fun save(expected: Person.Draft)
    suspend fun getById(id: User.Id): PersonVariant?

    fun getFiltered(page: Page, filter: Filter): Flow<Person>

    data class Filter(
        val firstName: Regex,
        val lastName: Regex,
        val height: IntRange,
        val birthday: ClosedRange<LocalDate>,
        val faculty: Set<Faculty.Id>,
    )
}
