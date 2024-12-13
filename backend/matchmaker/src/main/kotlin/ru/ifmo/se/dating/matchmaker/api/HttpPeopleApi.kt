package ru.ifmo.se.dating.matchmaker.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.matchmaker.api.generated.PeopleApiDelegate
import ru.ifmo.se.dating.matchmaker.logic.AttitudeService
import ru.ifmo.se.dating.matchmaker.logic.PersonService
import ru.ifmo.se.dating.matchmaker.model.Attitude
import ru.ifmo.se.dating.matchmaker.model.PersonUpdate
import ru.ifmo.se.dating.matchmaker.model.generated.AttitudeKindMessage
import ru.ifmo.se.dating.matchmaker.model.generated.PersonUpdateMessage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.spring.security.SpringSecurityContext

@Controller
class HttpPeopleApi(
    private val personService: PersonService,
    private val attitudeService: AttitudeService,
) : PeopleApiDelegate {
    override suspend fun peoplePersonIdAttitudesIncomingAttitudeKindPost(
        personId: Long,
        attitudeKind: AttitudeKindMessage,
    ): ResponseEntity<Unit> {
        val sourceId = SpringSecurityContext.principal()
        val targetId = User.Id(personId.toInt())
        val kind = attitudeKind.toModel()

        attitudeService.express(Attitude(sourceId, targetId, kind))

        return ResponseEntity.ok(Unit)
    }

    override fun peoplePersonIdMatchesGet(
        personId: Long,
    ): ResponseEntity<Flow<Long>> = flow {
        val sourceId = SpringSecurityContext.principal()
        val targetId = User.Id(personId.toInt())
        require(sourceId == targetId)

        attitudeService.matches(sourceId)
            .map { it.number.toLong() }
            .collect { emit(it) }
    }.let { ResponseEntity.ok(it) }

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

    private fun AttitudeKindMessage.toModel() = when (this) {
        AttitudeKindMessage.like -> Attitude.Kind.LIKE
        AttitudeKindMessage.skip -> Attitude.Kind.SKIP
    }
}
