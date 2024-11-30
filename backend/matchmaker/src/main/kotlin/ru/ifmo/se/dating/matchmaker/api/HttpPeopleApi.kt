package ru.ifmo.se.dating.matchmaker.api

import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.api.generated.PeopleApiDelegate
import ru.ifmo.se.dating.matchmaker.logic.PersonService
import ru.ifmo.se.dating.matchmaker.model.PersonUpdate
import ru.ifmo.se.dating.matchmaker.model.generated.AttitudeKindMessage
import ru.ifmo.se.dating.matchmaker.model.generated.PersonUpdateMessage
import ru.ifmo.se.dating.security.auth.User

@Controller
class HttpPeopleApi(
    private val personService: PersonService,
) : PeopleApiDelegate {
    override suspend fun peoplePersonIdAttitudesIncomingAttitudeKindPost(
        personId: Long,
        attitudeKind: AttitudeKindMessage,
    ): ResponseEntity<Unit> =
        ResponseEntityStub.create()

    override fun peoplePersonIdMatchesGet(
        personId: Long,
    ): ResponseEntity<Flow<Long>> =
        ResponseEntityStub.create()

    override suspend fun peoplePersonIdPut(
        personId: Long,
        personUpdateMessage: PersonUpdateMessage,
    ): ResponseEntity<Unit> {
        val update = personUpdateMessage.toModel(personId)
        personService.account(update)
        return ResponseEntity.ok(Unit)
    }

    private fun PersonUpdateMessage.toModel(personId: Long) = PersonUpdate(
        id = User.Id(personId.toInt()),
        version = PersonUpdate.Version(version),
    )
}
