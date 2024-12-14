package ru.ifmo.se.dating.matchmaker.logic.basic

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.logic.StatisticsService
import ru.ifmo.se.dating.matchmaker.model.AttitudesStatistics
import ru.ifmo.se.dating.matchmaker.storage.StatisticsStorage

@Service
class BasicStatisticsService(private val storage: StatisticsStorage) : StatisticsService {
    override fun selectAttitudesByPerson(): Flow<AttitudesStatistics> =
        storage.selectAttitudesByPerson()
}
