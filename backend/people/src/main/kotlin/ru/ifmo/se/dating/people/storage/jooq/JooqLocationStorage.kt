package ru.ifmo.se.dating.people.storage.jooq

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jooq.generated.tables.records.LocationRecord
import org.jooq.generated.tables.references.LOCATION
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.people.model.Coordinates
import ru.ifmo.se.dating.people.model.Location
import ru.ifmo.se.dating.people.storage.LocationStorage
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqLocationStorage(private val database: JooqDatabase) : LocationStorage {
    override suspend fun insert(draft: Location.Draft): Location = database.only {
        insertInto(LOCATION)
            .values(draft.toRecord())
            .returning()
    }.toModel()

    override suspend fun selectById(id: Location.Id): Location? = database.maybe {
        selectFrom(LOCATION)
            .where(LOCATION.ID.eq(id.number))
    }?.toModel()

    override fun selectAll(): Flow<Location> = database.flow {
        selectFrom(LOCATION)
    }.map { it.toModel() }

    fun Location.Draft.toRecord() = LocationRecord(
        name = name,
        latitude = coordinates.latitude,
        longitude = coordinates.longitude,
    )

    fun LocationRecord.toModel() = Location(
        id = Location.Id(id!!),
        name = name,
        coordinates = Coordinates(
            latitude = latitude,
            longitude = longitude,
        ),
    )
}
