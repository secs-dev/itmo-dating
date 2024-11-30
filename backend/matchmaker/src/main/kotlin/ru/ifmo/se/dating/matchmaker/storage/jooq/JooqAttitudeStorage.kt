package ru.ifmo.se.dating.matchmaker.storage.jooq

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jooq.generated.enums.AttitudeKind
import org.jooq.generated.tables.references.ATTITUDE
import org.jooq.generated.tables.references.PERSON
import org.jooq.impl.DSL.*
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.matchmaker.model.Attitude
import ru.ifmo.se.dating.matchmaker.storage.AttitudeStorage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqAttitudeStorage(private val database: JooqDatabase) : AttitudeStorage {
    override suspend fun insert(attitude: Attitude) = database.only {
        insertInto(ATTITUDE)
            .set(ATTITUDE.SOURCE_ID, attitude.sourceId.number)
            .set(ATTITUDE.TARGET_ID, attitude.targetId.number)
            .set(ATTITUDE.ATTITUDE_, attitude.kind.toRecord())
    }.let { }

    override fun selectUnknownFor(id: User.Id, limit: Int): Flow<User.Id> = database.flow {
        val attitudes =
            selectFrom(ATTITUDE)
                .where(
                    ATTITUDE.SOURCE_ID.eq(id.number)
                        .and(ATTITUDE.TARGET_ID.eq(PERSON.ACCOUNT_ID))
                )

        select(PERSON.ACCOUNT_ID)
            .from(PERSON)
            .where(PERSON.ACCOUNT_ID.ne(id.number).and(notExists(attitudes)))
            .limit(limit)
    }.map { User.Id(it[PERSON.ACCOUNT_ID.name] as Int) }

    private fun Attitude.Kind.toRecord() = when (this) {
        Attitude.Kind.LIKE -> AttitudeKind.like
        Attitude.Kind.SKIP -> AttitudeKind.skip
    }
}
