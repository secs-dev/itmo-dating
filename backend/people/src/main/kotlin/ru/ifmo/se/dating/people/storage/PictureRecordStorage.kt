package ru.ifmo.se.dating.people.storage

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.model.Picture

interface PictureRecordStorage {
    suspend fun insert(): Picture
    suspend fun setIsReferenced(id: Picture.Id, isReferenced: Boolean)
    suspend fun delete(id: Picture.Id)
    fun selectAbandoned(): Flow<Picture>
}
