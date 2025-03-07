package ru.ifmo.se.dating.people.logic.basic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.logic.LocationService
import ru.ifmo.se.dating.people.model.Location
import ru.ifmo.se.dating.people.storage.LocationStorage

class BasicLocationService(private val storage: LocationStorage) : LocationService {
    override suspend fun create(draft: Location.Draft): Location =
        storage.insert(draft)

    override suspend fun getById(id: Location.Id): Location? =
        storage.selectById(id)

    override suspend fun getAll(): Flow<Location> =
        storage.selectAll()
}
