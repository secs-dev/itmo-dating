package ru.ifmo.se.dating.people.storage.jooq

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jooq.generated.tables.records.FacultyRecord
import org.jooq.generated.tables.references.FACULTY
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.storage.FacultyStorage
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqFacultyStorage(private val database: JooqDatabase) : FacultyStorage {
    override suspend fun insert(draft: Faculty.Draft): Faculty = database.only {
        insertInto(FACULTY)
            .values(draft.toRecord())
            .returning()
    }.toModel()

    override suspend fun selectById(id: Faculty.Id): Faculty? = database.maybe {
        selectFrom(FACULTY)
            .where(FACULTY.ID.eq(id.number))
    }?.toModel()

    override fun selectAll(): Flow<Faculty> = database.flow {
        selectFrom(FACULTY)
    }.map { it.toModel() }

    fun Faculty.Draft.toRecord() = FacultyRecord(
        longName = longName,
    )

    fun FacultyRecord.toModel() = Faculty(
        id = Faculty.Id(id!!),
        longName = longName,
    )
}
