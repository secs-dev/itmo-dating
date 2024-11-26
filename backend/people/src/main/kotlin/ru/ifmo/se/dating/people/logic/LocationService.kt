package ru.ifmo.se.dating.people.logic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.model.Location

interface LocationService {
    suspend fun create(draft: Location.Draft): Location
    suspend fun getById(id: Location.Id): Location?
    fun getAll(): Flow<Location>
}
