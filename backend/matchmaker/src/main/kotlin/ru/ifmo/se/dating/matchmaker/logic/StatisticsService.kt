package ru.ifmo.se.dating.matchmaker.logic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.matchmaker.model.AttitudesStatistics

interface StatisticsService {
    suspend fun selectAttitudesByPerson(): Flow<AttitudesStatistics>
}
