package ru.ifmo.se.dating.people.storage

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.model.Location

interface LocationStorage {
    suspend fun insert(draft: Location.Draft): Location
    suspend fun selectById(id: Location.Id): Location?
    fun selectAll(): Flow<Location>
}
