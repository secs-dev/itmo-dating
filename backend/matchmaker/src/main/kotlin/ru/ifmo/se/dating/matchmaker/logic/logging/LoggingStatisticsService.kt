package ru.ifmo.se.dating.matchmaker.logic.logging

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.matchmaker.logic.StatisticsService
import ru.ifmo.se.dating.matchmaker.model.AttitudesStatistics

class LoggingStatisticsService(private val origin: StatisticsService) : StatisticsService {
    private val log = autoLog()

    override fun selectAttitudesByPerson(): Flow<AttitudesStatistics> =
        runCatching { origin.selectAttitudesByPerson() }
            .onSuccess { log.warn("Someone got attitudes by person statistics") }
            .onFailure { e -> log.warn("Failed to got statistics: ${e.message}") }
            .getOrThrow()
}
