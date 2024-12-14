package ru.ifmo.se.dating.matchmaker.storage.jooq

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jooq.generated.enums.AttitudeKind
import org.jooq.generated.tables.references.ATTITUDE
import org.jooq.generated.tables.references.PERSON
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.matchmaker.model.AttitudesStatistics
import ru.ifmo.se.dating.matchmaker.storage.StatisticsStorage
import ru.ifmo.se.dating.matchmaker.storage.jooq.mapping.toModel
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqStatisticsStorage(private val database: JooqDatabase) : StatisticsStorage {
    override fun selectAttitudesByPerson(): Flow<AttitudesStatistics> = database.flow {
        select(
            PERSON.ACCOUNT_ID,
            DSL.sum(DSL.`when`(ATTITUDE.KIND.eq(AttitudeKind.like), 1).otherwise(0)),
            DSL.sum(DSL.`when`(ATTITUDE.KIND.eq(AttitudeKind.skip), 1).otherwise(0)),
        )
            .from(PERSON, ATTITUDE)
            .where(PERSON.ACCOUNT_ID.eq(ATTITUDE.SOURCE_ID))
            .groupBy(PERSON.ACCOUNT_ID)
    }.map { it.toModel() }
}
