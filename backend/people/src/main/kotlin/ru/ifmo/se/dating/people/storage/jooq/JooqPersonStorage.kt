package ru.ifmo.se.dating.people.storage.jooq

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import org.jooq.generated.tables.records.PersonInterestRecord
import org.jooq.generated.tables.records.PersonRecord
import org.jooq.generated.tables.references.PERSON
import org.jooq.generated.tables.references.PERSON_INTEREST
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.Topic
import ru.ifmo.se.dating.people.storage.PersonStorage
import ru.ifmo.se.dating.storage.TxEnv
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqPersonStorage(
    private val database: JooqDatabase,
    private val tx: TxEnv,
) : PersonStorage {
    override suspend fun create(draft: Person.Draft): Person = tx.transactional {
        val person = database.only {
            insertInto(PERSON)
                .values(draft.toRecord())
                .returning()
        }.toModel()

        database.only {
            batchInsert(draft.interests.map { it.toRecord(person.id) })
        }

        person.copy(interests = draft.interests)
    }

    override fun getAll(): Flow<Person> = tx.transactional(
        database
            .flow { selectFrom(PERSON) }
            .map { it.toModel() }
            .map { it.copy(interests = getInterests(it.id)) }
    )

    private suspend fun getInterests(personId: Person.Id): Set<Person.Interest> = database.flow {
        selectFrom(PERSON_INTEREST)
            .where(PERSON.ID.eq(personId.number))
    }.map { it.toModel() }.toCollection(mutableSetOf())

    private fun Person.Draft.toRecord() = PersonRecord(
        firstName = firstName.text,
        lastName = lastName.text,
        height = height,
        birthday = birthday,
        facultyId = facultyId.number,
    )

    private fun PersonRecord.toModel() = Person(
        id = Person.Id(id!!),
        firstName = Person.Name(firstName),
        lastName = Person.Name(lastName),
        height = height,
        birthday = birthday,
        facultyId = Faculty.Id(facultyId),
        interests = emptySet(),
    )

    private fun Person.Interest.toRecord(personId: Person.Id) = PersonInterestRecord(
        personId = personId.number,
        topicId = topicId.number,
        rank = rank,
    )

    private fun PersonInterestRecord.toModel() = Person.Interest(
        topicId = Topic.Id(topicId),
        rank = rank,
    )
}
