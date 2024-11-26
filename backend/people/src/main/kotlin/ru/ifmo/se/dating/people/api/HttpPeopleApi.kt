package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.exception.orThrowNotFound
import ru.ifmo.se.dating.people.api.generated.PeopleApiDelegate
import ru.ifmo.se.dating.people.logic.PersonService
import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.model.Location
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.people.model.generated.FacultyMessage
import ru.ifmo.se.dating.people.model.generated.PersonDraftMessage
import ru.ifmo.se.dating.people.model.generated.PersonMessage
import ru.ifmo.se.dating.people.model.generated.PersonVariantMessage
import ru.ifmo.se.dating.people.model.generated.ZodiacSignMessage
import ru.ifmo.se.dating.people.model.generated.PersonStatusMessage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.validation.expect
import java.time.LocalDate
import java.time.OffsetDateTime

@Controller
internal class HttpPeopleApi(private val service: PersonService) : PeopleApiDelegate {
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

    override suspend fun peoplePersonIdGet(personId: Long): ResponseEntity<PersonVariantMessage> =
        service.getById(User.Id(personId.toInt()))
            ?.toMessage()
            .orThrowNotFound("person with id $personId not found")
            .let { ResponseEntity.ok(it) }

    override suspend fun peoplePersonIdPatch(
        personId: Long,
        personDraftMessage: PersonDraftMessage,
    ): ResponseEntity<Unit> {
        expect(personId == personDraftMessage.userId) {
            append("Path provided personId does not match with body")
        }

        val status = personDraftMessage.status
        val model = personDraftMessage.toModel()

        when (status) {
            PersonStatusMessage.draft -> service.edit(model)
            PersonStatusMessage.ready -> service.save(model)
        }

        return ResponseEntity.ok(Unit)
    }

    companion object {
        fun PersonDraftMessage.toModel() = Person.Draft(
            id = User.Id(userId.toInt()),
            firstName = firstName?.let { Person.Name(it) },
            lastName = lastName?.let { Person.Name(it) },
            height = height,
            birthday = birthday,
            facultyId = facultyId?.let { Faculty.Id(it.toInt()) },
            locationId = locationId?.let { Location.Id(it.toInt()) },
        )

        fun PersonVariant.toMessage() = when (this) {
            is Person.Draft -> toMessage()
            is Person -> toMessage()
        }

        private fun Person.toMessage() = PersonMessage(
            status = PersonStatusMessage.ready,
            userId = id.number.toLong(),
            firstName = firstName.text,
            lastName = lastName.text,
            height = height,
            birthday = birthday,
            facultyId = facultyId.number.toLong(),
            locationId = locationId.number.toLong(),
            interests = emptySet(),
            zodiac = ZodiacSignMessage.leo,
        )

        private fun Person.Draft.toMessage() = PersonDraftMessage(
            status = PersonStatusMessage.draft,
            userId = id.number.toLong(),
            firstName = firstName?.text,
            lastName = lastName?.text,
            height = height,
            birthday = birthday,
            facultyId = facultyId?.number?.toLong(),
            locationId = locationId?.number?.toLong(),
            interests = emptySet(),
        )
    }
}
