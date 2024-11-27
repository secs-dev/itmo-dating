package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.exception.orThrowNotFound
import ru.ifmo.se.dating.pagging.Page
import ru.ifmo.se.dating.people.api.generated.PeopleApiDelegate
import ru.ifmo.se.dating.people.logic.PersonService
import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.model.Location
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.people.model.generated.*
import ru.ifmo.se.dating.security.auth.User
import java.time.LocalDate
import java.time.OffsetDateTime

@Controller
class HttpPeopleApi(private val service: PersonService) : PeopleApiDelegate {
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
        faculty: List<Long>?,
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
        updatedMin: OffsetDateTime?,
        updatedMax: OffsetDateTime?,
        sortBy: List<PersonSortingKeyMessage>?,
    ): ResponseEntity<Flow<PersonMessage>> {
        if (listOfNotNull(
                picturesCountMin,
                picturesCountMax,
                topicId,
                zodiac,
                latitude,
                longitude,
                radius,
                updatedMin,
                updatedMax,
                sortBy
            ).isNotEmpty()
        ) {
            TODO("Unsupported GET /people query parameter was provided")
        }

        return service.getFiltered(
            Page(offset = offset.toInt(), limit = limit.toInt()),
            PersonService.Filter(
                firstName = firstName?.let { Regex(it) } ?: Regex(".*"),
                lastName = lastName?.let { Regex(it) } ?: Regex(".*"),
                height = (heightMin ?: Int.MIN_VALUE)..(heightMax ?: Int.MAX_VALUE),
                birthday = (birthdayMin ?: LocalDate.MIN)..(birthdayMax ?: LocalDate.MAX),
                faculty = (faculty ?: listOf()).map { Faculty.Id(it.toInt()) }.toSet(),
            ),
        ).map { it.toMessage() }.let { ResponseEntity.ok(it) }
    }

    override suspend fun peoplePersonIdDelete(personId: Long): ResponseEntity<Unit> =
        ResponseEntityStub.create()

    override suspend fun peoplePersonIdGet(personId: Long): ResponseEntity<PersonVariantMessage> =
        service.getById(User.Id(personId.toInt()))
            ?.toMessage()
            .orThrowNotFound("person with id $personId not found")
            .let { ResponseEntity.ok(it) }

    override suspend fun peoplePersonIdPatch(
        personId: Long,
        personPatchMessage: PersonPatchMessage,
    ): ResponseEntity<Unit> {
        val status = personPatchMessage.status
        val model = personPatchMessage.toModel(personId.toInt())

        when (status) {
            PersonStatusMessage.draft -> service.edit(model)
            PersonStatusMessage.ready -> service.save(model)
        }

        return ResponseEntity.ok(Unit)
    }

    companion object {
        fun PersonPatchMessage.toModel(id: Int) = Person.Draft(
            id = User.Id(id),
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
