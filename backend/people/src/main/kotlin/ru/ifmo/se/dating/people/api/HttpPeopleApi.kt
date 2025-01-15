package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.exception.AuthorizationException
import ru.ifmo.se.dating.exception.orThrowNotFound
import ru.ifmo.se.dating.logging.Log
import ru.ifmo.se.dating.logging.Log.Companion.autoLog
import ru.ifmo.se.dating.pagging.Page
import ru.ifmo.se.dating.people.api.generated.PeopleApiDelegate
import ru.ifmo.se.dating.people.api.mapping.toMessage
import ru.ifmo.se.dating.people.api.mapping.toModel
import ru.ifmo.se.dating.people.logic.PersonService
import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.model.generated.*
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.spring.security.auth.SpringSecurityContext
import java.time.LocalDate
import java.time.OffsetDateTime

@Controller
class HttpPeopleApi(private val service: PersonService) : PeopleApiDelegate {
    private val log = Log.autoLog()

    private var lastPicture: ByteArray = ByteArray(0)

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

    override suspend fun peoplePersonIdDelete(personId: Long): ResponseEntity<Unit> {
        val callerId = SpringSecurityContext.principal()
        val targetId = User.Id(personId.toInt())
        if (callerId != targetId) {
            throw AuthorizationException("caller $callerId can't delete $targetId")
        }

        service.delete(targetId)

        return ResponseEntity.ok(Unit)
    }

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

    override suspend fun peoplePersonIdPhotosPost(
        personId: Long, body: Resource?,
    ): ResponseEntity<PictureMessage> {
        require(body != null)

        val callerId = SpringSecurityContext.principal()
        val targetId = User.Id(personId.toInt())
        if (callerId != targetId) {
            throw AuthorizationException("caller $callerId can't post photo to $targetId profile")
        }

        log.info("POST picture with size ${body.contentLength()}")
        lastPicture = body.contentAsByteArray

        return PictureMessage(id = 666).let { ResponseEntity.ok(it) }
    }

    override suspend fun peoplePersonIdPhotosPictureIdGet(
        personId: Long,
        pictureId: Long,
    ): ResponseEntity<Resource> {
        return ByteArrayResource(lastPicture).let { ResponseEntity.ok(it) }
    }
}
