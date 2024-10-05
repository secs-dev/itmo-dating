package ru.ifmo.se.dating.matchmaker.api

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.api.generated.PeopleApiDelegate
import ru.ifmo.se.dating.matchmaker.model.generated.AttitudeKind

@Service
internal class HttpPeopleApi : PeopleApiDelegate {
    override fun peoplePersonIdAttitudesIncomingAttitudeKindPost(
        personId: Long, attitudeKind: AttitudeKind
    ): ResponseEntity<Void> =
        ResponseEntityStub.create()

    override fun peoplePersonIdMatchesGet(personId: Long): ResponseEntity<Set<Long>> =
        ResponseEntityStub.create()
}
