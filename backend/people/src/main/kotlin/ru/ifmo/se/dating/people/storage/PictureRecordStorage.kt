package ru.ifmo.se.dating.people.storage

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.model.Picture
import ru.ifmo.se.dating.security.auth.User

interface PictureRecordStorage {
    suspend fun insert(ownerId: User.Id): Picture
    suspend fun setIsReferenced(id: Picture.Id, isReferenced: Boolean)
    suspend fun delete(id: Picture.Id)
    suspend fun selectAbandoned(): Flow<Picture>
    fun selectByOwner(ownerId: User.Id): Flow<Picture>
}
