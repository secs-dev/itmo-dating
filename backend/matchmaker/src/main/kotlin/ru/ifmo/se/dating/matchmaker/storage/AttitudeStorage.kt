package ru.ifmo.se.dating.matchmaker.storage

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.matchmaker.model.Attitude
import ru.ifmo.se.dating.security.auth.User

interface AttitudeStorage {
    suspend fun insert(attitude: Attitude)
    fun selectUnknownFor(id: User.Id, limit: Int): Flow<User.Id>
}
