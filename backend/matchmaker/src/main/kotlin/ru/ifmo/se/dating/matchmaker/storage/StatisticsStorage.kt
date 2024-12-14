package ru.ifmo.se.dating.matchmaker.storage

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.matchmaker.model.AttitudesStatistics

interface StatisticsStorage {
    fun selectAttitudesByPerson(): Flow<AttitudesStatistics>
}
