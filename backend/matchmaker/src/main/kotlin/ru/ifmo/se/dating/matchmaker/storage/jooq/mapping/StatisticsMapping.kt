package ru.ifmo.se.dating.matchmaker.storage.jooq.mapping

import org.jooq.Record3
import ru.ifmo.se.dating.matchmaker.model.AttitudesStatistics
import ru.ifmo.se.dating.security.auth.User
import java.math.BigDecimal

typealias AttitudesStatisticsRecord = Record3<Int?, BigDecimal, BigDecimal>

fun AttitudesStatisticsRecord.toModel(): AttitudesStatistics {
    val (id, likes, skips) = this
    return AttitudesStatistics(
        userId = User.Id(id!!),
        likes = likes.toLong(),
        skips = skips.toLong(),
    )
}
