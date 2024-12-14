package ru.ifmo.se.dating.matchmaker.api.mapping

import ru.ifmo.se.dating.matchmaker.model.AttitudesStatistics
import ru.ifmo.se.dating.matchmaker.model.generated.StatisticsAttitudesGet200ResponseInnerMessage

fun AttitudesStatistics.toMessage() =
    StatisticsAttitudesGet200ResponseInnerMessage(
        personId = userId.number.toLong(),
        likes = likes.toInt(),
        skips = skips.toInt(),
    )
