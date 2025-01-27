package ru.ifmo.se.dating.matchmaker.soap

import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.*

@Controller
class MatchmakerSoapService(
    private val rest: MatchmakerRestClient,
) : ITMODatingMatchmakerPortType {
    override fun getSuggestions(parameters: GetSuggestionsRequest): GetSuggestionsResponse =
        rest.suggestions
            .suggestionsGet(limit = parameters.limit.toLong())
            .toSuggestionsSoap()

    override fun likeSkip(parameters: LikeSkipRequest): LikeSkipResponse =
        rest.suggestions
            .peoplePersonIdAttitudesIncomingAttitudeKindPost(
                personId = parameters.personId.toLong(),
                attitudeKind = parameters.attitudeKind.toRest(),
            )
            .let { LikeSkipResponse() }

    override fun getAttitudesStatistics(parameters: Any): GetAttitudesStatisticsResponse =
        rest.statistics
            .statisticsAttitudesGet()
            .toSoap()

    override fun getMatches(parameters: GetMatchesRequest): GetMatchesResponse =
        rest.suggestions
            .peoplePersonIdMatchesGet(personId = parameters.personId.toLong())
            .toMatchesSoap()

    override fun updatePerson(parameters: UpdatePersonRequest): UpdatePersonResponse =
        rest.people
            .peoplePersonIdPut(
                personId = parameters.personId.toLong(),
                personUpdateMessage = parameters.personUpdate.toRest(),
            )
            .let { UpdatePersonResponse() }

    override fun resetAttitudes(parameters: ResetAttitudesRequest): ResetAttitudesResponse =
        rest.suggestions
            .attitudesDelete(sourceId = parameters.sourceId.toLong())
            .let { ResetAttitudesResponse() }
}
