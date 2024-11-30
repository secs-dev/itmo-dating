package ru.ifmo.se.dating.matchmaker.storage.jooq

import org.jooq.generated.enums.AttitudeKind
import org.jooq.generated.tables.references.ATTITUDE
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.matchmaker.model.Attitude
import ru.ifmo.se.dating.matchmaker.storage.AttitudeStorage
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqAttitudeStorage(private val database: JooqDatabase) : AttitudeStorage {
    override suspend fun insert(attitude: Attitude) = database.only {
        insertInto(ATTITUDE)
            .set(ATTITUDE.SOURCE_ID, attitude.sourceId.number)
            .set(ATTITUDE.TARGET_ID, attitude.targetId.number)
            .set(ATTITUDE.ATTITUDE_, attitude.kind.toRecord())
    }.let { }

    private fun Attitude.Kind.toRecord() = when (this) {
        Attitude.Kind.LIKE -> AttitudeKind.like
        Attitude.Kind.SKIP -> AttitudeKind.skip
    }
}
