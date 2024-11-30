package ru.ifmo.se.dating.matchmaker.logic

import ru.ifmo.se.dating.matchmaker.model.Attitude

interface AttitudeService {
    suspend fun express(attitude: Attitude)
}
