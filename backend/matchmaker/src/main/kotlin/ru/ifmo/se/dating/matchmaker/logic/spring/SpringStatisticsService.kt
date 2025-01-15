package ru.ifmo.se.dating.matchmaker.logic.spring

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.logic.StatisticsService
import ru.ifmo.se.dating.matchmaker.logic.basic.BasicStatisticsService
import ru.ifmo.se.dating.matchmaker.logic.logging.LoggingStatisticsService
import ru.ifmo.se.dating.matchmaker.storage.StatisticsStorage

@Service
class SpringStatisticsService(
    storage: StatisticsStorage
) : StatisticsService by
LoggingStatisticsService(
    BasicStatisticsService(
        storage = storage,
    )
)
