package ru.ifmo.se.dating.matchmaker.api

import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.api.generated.PeopleApiDelegate
import ru.ifmo.se.dating.matchmaker.model.generated.AttitudeKindMessage

@Service
internal class HttpPeopleApi : PeopleApiDelegate {
    override suspend fun peoplePersonIdAttitudesIncomingAttitudeKindPost(
        personId: Long,
        attitudeKind: AttitudeKindMessage,
    ): ResponseEntity<Unit> =
        ResponseEntityStub.create()

    override fun peoplePersonIdMatchesGet(
        personId: Long,
    ): ResponseEntity<Flow<Long>> =
        ResponseEntityStub.create()
}
