package ru.ifmo.se.dating.matchmaker.logic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.matchmaker.model.Attitude
import ru.ifmo.se.dating.security.auth.User

interface AttitudeService {
    suspend fun express(attitude: Attitude)
    suspend fun matches(client: User.Id): Flow<User.Id>
    suspend fun suggestions(client: User.Id, limit: Int): Flow<User.Id>
}
