package ru.ifmo.se.dating.matchmaker.soap.server.rest

import ru.ifmo.se.dating.matchmaker.AttitudeKind
import ru.ifmo.se.dating.matchmaker.GetAttitudesStatisticsResponse
import ru.ifmo.se.dating.matchmaker.PersonStatus
import ru.ifmo.se.dating.matchmaker.PersonUpdate
import ru.ifmo.se.dating.matchmaker.server.model.generated.AttitudeKindMessage
import ru.ifmo.se.dating.matchmaker.server.model.generated.PersonStatusMessage
import ru.ifmo.se.dating.matchmaker.server.model.generated.PersonUpdateMessage
import ru.ifmo.se.dating.matchmaker.server.model.generated.StatisticsAttitudesGet200ResponseInnerMessage

typealias StatisticsEntry = StatisticsAttitudesGet200ResponseInnerMessage

fun AttitudeKindMessage.toSoap(): AttitudeKind =
    when (this) {
        AttitudeKindMessage.like -> AttitudeKind.LIKE
        AttitudeKindMessage.skip -> AttitudeKind.SKIP
    }

fun PersonStatusMessage.toSoap(): PersonStatus =
    when (this) {
        PersonStatusMessage.hidden -> PersonStatus.HIDDEN
        PersonStatusMessage.active -> PersonStatus.ACTIVE
    }

fun PersonUpdateMessage.toSoap(): PersonUpdate =
    PersonUpdate().also {
        it.status = this@toSoap.status.toSoap()
        it.version = this@toSoap.version
    }

fun GetAttitudesStatisticsResponse.Statistics.toRest(): StatisticsEntry =
    StatisticsEntry(
        personId = personId.toLong(),
        likes = likes,
        skips - skips,
    )
