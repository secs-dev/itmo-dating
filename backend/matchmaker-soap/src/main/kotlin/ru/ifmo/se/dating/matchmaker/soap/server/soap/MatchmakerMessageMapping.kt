package ru.ifmo.se.dating.matchmaker.soap.server.soap

import ru.ifmo.se.dating.matchmaker.*
import ru.ifmo.se.dating.matchmaker.client.model.generated.AttitudeKindMessage
import ru.ifmo.se.dating.matchmaker.client.model.generated.PersonStatusMessage
import ru.ifmo.se.dating.matchmaker.client.model.generated.PersonUpdateMessage
import ru.ifmo.se.dating.matchmaker.client.model.generated.StatisticsAttitudesGet200ResponseInnerMessage

fun List<Long>.toSuggestionsSoap(): GetSuggestionsResponse =
    GetSuggestionsResponse().apply {
        personId.addAll(this@toSuggestionsSoap.map { it.toInt() })
    }

fun AttitudeKind.toRest(): AttitudeKindMessage =
    when (this) {
        AttitudeKind.LIKE -> AttitudeKindMessage.like
        AttitudeKind.SKIP -> AttitudeKindMessage.skip
    }

fun List<StatisticsAttitudesGet200ResponseInnerMessage>.toSoap(): GetAttitudesStatisticsResponse =
    GetAttitudesStatisticsResponse().apply {
        statistics.addAll(this@toSoap.map { it.toSoap() })
    }

fun StatisticsAttitudesGet200ResponseInnerMessage.toSoap() =
    GetAttitudesStatisticsResponse.Statistics().apply {
        personId = this@toSoap.personId.toInt()
        likes = this@toSoap.likes
        skips = this@toSoap.skips
    }

fun List<Long>.toMatchesSoap(): GetMatchesResponse =
    GetMatchesResponse().apply {
        personId.addAll(this@toMatchesSoap.map { it.toInt() })
    }

fun PersonUpdate.toRest(): PersonUpdateMessage =
    PersonUpdateMessage(
        status = this.status.toRest(),
        version = this.version,
    )

fun PersonStatus.toRest(): PersonStatusMessage =
    when (this) {
        PersonStatus.HIDDEN -> PersonStatusMessage.hidden
        PersonStatus.ACTIVE -> PersonStatusMessage.active
    }
