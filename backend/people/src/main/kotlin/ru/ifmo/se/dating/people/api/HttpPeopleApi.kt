package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.people.api.generated.PeopleApiDelegate
import ru.ifmo.se.dating.people.model.generated.FacultyMessage
import ru.ifmo.se.dating.people.model.generated.PersonDraftMessage
import ru.ifmo.se.dating.people.model.generated.PersonMessage
import ru.ifmo.se.dating.people.model.generated.ZodiacSignMessage
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

@Controller
internal class HttpPeopleApi : PeopleApiDelegate {
    override fun peopleGet(
        offset: Long,
        limit: Long,
        firstName: String?,
        lastName: String?,
        picturesCountMin: Int?,
        picturesCountMax: Int?,
        topicId: List<Long>?,
        heightMin: Int?,
        heightMax: Int?,
        birthdayMin: LocalDate?,
        birthdayMax: LocalDate?,
        zodiac: List<ZodiacSignMessage>?,
        faculty: List<FacultyMessage>?,
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
        updatedMin: OffsetDateTime?,
        updatedMax: OffsetDateTime?,
        sortBy: List<String>?,
    ): ResponseEntity<Flow<PersonMessage>> = ResponseEntityStub.create()

    override suspend fun peoplePersonIdDelete(personId: Long): ResponseEntity<Unit> =
        ResponseEntityStub.create()

    override suspend fun peoplePersonIdGet(personId: Long): ResponseEntity<PersonMessage> =
        ResponseEntityStub.create()

    override suspend fun peoplePersonIdPatch(
        personId: Long,
        personDraftMessage: PersonDraftMessage,
    ): ResponseEntity<Unit> = ResponseEntityStub.create()

    override suspend fun peoplePost(
        idempotencyKey: UUID,
        personDraftMessage: PersonDraftMessage,
    ): ResponseEntity<PersonMessage> = ResponseEntityStub.create()
}
