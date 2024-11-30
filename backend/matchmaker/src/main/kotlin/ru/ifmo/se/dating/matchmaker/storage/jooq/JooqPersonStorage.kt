package ru.ifmo.se.dating.matchmaker.storage.jooq

import org.jooq.generated.tables.records.PersonRecord
import org.jooq.generated.tables.references.PERSON
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.matchmaker.model.PersonUpdate
import ru.ifmo.se.dating.matchmaker.storage.PersonStorage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqPersonStorage(private val database: JooqDatabase) : PersonStorage {
    override suspend fun selectById(id: User.Id): PersonUpdate? = database.maybe {
        selectFrom(PERSON)
            .where(PERSON.ACCOUNT_ID.eq(id.number))
    }?.toModel()

    override suspend fun upsert(person: PersonUpdate) = database.only {
        insertInto(PERSON)
            .set(PERSON.ACCOUNT_ID, person.id.number)
            .set(PERSON.VERSION, person.version.number)
            .onConflict()
            .doUpdate()
            .set(PERSON.VERSION, person.version.number)
    }.let { }

    private fun PersonRecord.toModel() = PersonUpdate(
        id = User.Id(accountId),
        version = PersonUpdate.Version(version),
    )
}
