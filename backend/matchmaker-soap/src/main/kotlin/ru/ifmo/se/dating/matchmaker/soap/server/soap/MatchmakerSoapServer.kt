package ru.ifmo.se.dating.matchmaker.soap.server.soap

import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.*
import ru.ifmo.se.dating.matchmaker.soap.client.rest.MatchmakerRestClient

@Controller
class MatchmakerSoapServer(
    private val rest: MatchmakerRestClient,
) : ITMODatingMatchmakerPortType {
    override fun getSuggestions(
        authorization: String,
        parameters: GetSuggestionsRequest,
    ): GetSuggestionsResponse =
        rest.suggestions(authorization = authorization)
            .suggestionsGet(limit = parameters.limit.toLong())
            .toSuggestionsSoap()

    override fun likeSkip(
        authorization: String,
        parameters: LikeSkipRequest,
    ): LikeSkipResponse =
        rest.suggestions(authorization = authorization)
            .peoplePersonIdAttitudesIncomingAttitudeKindPost(
                personId = parameters.personId.toLong(),
                attitudeKind = parameters.attitudeKind.toRest(),
            )
            .let { LikeSkipResponse() }

    override fun getAttitudesStatistics(parameters: Any): GetAttitudesStatisticsResponse =
        rest.statistics()
            .statisticsAttitudesGet()
            .toSoap()

    override fun getMatches(
        authorization: String,
        parameters: GetMatchesRequest,
    ): GetMatchesResponse =
        rest.suggestions(authorization)
            .peoplePersonIdMatchesGet(personId = parameters.personId.toLong())
            .toMatchesSoap()

    override fun updatePerson(parameters: UpdatePersonRequest): UpdatePersonResponse =
        rest.people()
            .peoplePersonIdPut(
                personId = parameters.personId.toLong(),
                personUpdateMessage = parameters.personUpdate.toRest(),
            )
            .let { UpdatePersonResponse() }

    override fun resetAttitudes(
        authorization: String,
        parameters: ResetAttitudesRequest,
    ): ResetAttitudesResponse =
        rest.suggestions(authorization)
            .attitudesDelete(sourceId = parameters.sourceId.toLong())
            .let { ResetAttitudesResponse() }
}
