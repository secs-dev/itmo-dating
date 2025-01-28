package ru.ifmo.se.dating.matchmaker.soap.server.rest

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.GetMatchesRequest
import ru.ifmo.se.dating.matchmaker.LikeSkipRequest
import ru.ifmo.se.dating.matchmaker.UpdatePersonRequest
import ru.ifmo.se.dating.matchmaker.server.api.generated.PeopleApiDelegate
import ru.ifmo.se.dating.matchmaker.server.model.generated.AttitudeKindMessage
import ru.ifmo.se.dating.matchmaker.server.model.generated.PersonUpdateMessage
import ru.ifmo.se.dating.matchmaker.soap.client.soap.MatchmakerSoapClient

@Controller
class PeopleEndpoint(private val client: MatchmakerSoapClient) : PeopleApiDelegate {
    override fun peoplePersonIdAttitudesIncomingAttitudeKindPost(
        personId: Long,
        attitudeKind: AttitudeKindMessage
    ): ResponseEntity<Unit> = client
        .likeSkip(
            SpringHeaders.getAuthorization(),
            LikeSkipRequest().also {
                it.personId = personId.toInt()
                it.attitudeKind = attitudeKind.toSoap()
            }
        )
        .let { ResponseEntity.ok(Unit) }

    override fun peoplePersonIdMatchesGet(personId: Long): ResponseEntity<List<Long>> = client
        .getMatches(
            SpringHeaders.getAuthorization(),
            GetMatchesRequest().also {
                it.personId = personId.toInt()
            },
        )
        .let { people -> ResponseEntity.ok(people.personId.map { it.toLong() }) }

    override fun peoplePersonIdPut(
        personId: Long,
        personUpdateMessage: PersonUpdateMessage,
    ): ResponseEntity<Unit> = client
        .updatePerson(
            UpdatePersonRequest().also {
                it.personId = personId.toInt()
                it.personUpdate = personUpdateMessage.toSoap()
            }
        )
        .let { ResponseEntity.ok(Unit) }
}
