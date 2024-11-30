package ru.ifmo.se.dating.matchmaker.storage

import ru.ifmo.se.dating.matchmaker.model.Attitude

interface AttitudeStorage {
    suspend fun insert(attitude: Attitude)
}
