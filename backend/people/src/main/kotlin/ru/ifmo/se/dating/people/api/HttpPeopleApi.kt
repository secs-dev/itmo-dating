package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.exception.AuthorizationException
import ru.ifmo.se.dating.exception.InvalidValueException
import ru.ifmo.se.dating.exception.orThrowNotFound
import ru.ifmo.se.dating.pagging.Page
import ru.ifmo.se.dating.people.api.generated.PeopleApiDelegate
import ru.ifmo.se.dating.people.api.mapping.toMessage
import ru.ifmo.se.dating.people.api.mapping.toModel
import ru.ifmo.se.dating.people.logic.InterestService
import ru.ifmo.se.dating.people.logic.PersonService
import ru.ifmo.se.dating.people.logic.PictureService
import ru.ifmo.se.dating.people.model.*
import ru.ifmo.se.dating.people.model.generated.*
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.spring.security.auth.SpringSecurityContext
import java.time.LocalDate
import java.time.OffsetDateTime

@Suppress("TooManyFunctions")
@Controller
class HttpPeopleApi(
    private val personService: PersonService,
    private val interestService: InterestService,
    private val pictureService: PictureService,
) : PeopleApiDelegate {
    @Suppress("LongMethod", "CyclomaticComplexMethod", "MagicNumber", "CognitiveComplexMethod")
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
        zodiac: ZodiacSignMessage?,
        facultyId: Long?,
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
        updatedMin: OffsetDateTime?,
        updatedMax: OffsetDateTime?,
        sortBy: List<PersonSortingKeyMessage>?,
    ): ResponseEntity<Flow<PersonMessage>> {
        if (!sortBy.isNullOrEmpty()) {
            TODO("Unsupported GET /people query parameter was provided")
        }

        val area = when (listOfNotNull(latitude, longitude, radius).size) {
            0 -> {
                null
            }

            3 -> {
                Area(
                    center = Coordinates(
                        latitude = latitude!!,
                        longitude = longitude!!
                    ),
                    radius = radius!!.toDouble(),
                )
            }

            else -> {
                buildString {
                    append("Expected a complete area, but got ")
                    append("(latitude: $latitude, longitude: $longitude, radius: $radius)")
                }.let { throw InvalidValueException(it) }
            }
        }

        return personService.getFiltered(
            Page(offset = offset.toInt(), limit = limit.toInt()),
            PersonService.Filter(
                firstName = firstName?.let { Regex(it) },
                lastName = lastName?.let { Regex(it) },
                height = (heightMin ?: Int.MIN_VALUE)..(heightMax ?: Int.MAX_VALUE),
                birthday = (birthdayMin ?: LocalDate.MIN)..(birthdayMax ?: LocalDate.MAX),
                facultyId = facultyId?.let { Faculty.Id(it.toInt()) },
                updated = (updatedMin ?: OffsetDateTime.MIN)..(updatedMax ?: OffsetDateTime.MAX),
                area = area,
                picturesCount =
                (picturesCountMin ?: Int.MIN_VALUE)..(picturesCountMax ?: Int.MAX_VALUE),
                zodiac = zodiac?.toModel(),
                topicIds = topicId?.map { Topic.Id(it.toInt()) }?.toSet() ?: emptySet(),
            ),
        ).map { it.toMessage() }.let { ResponseEntity.ok(it) }
    }

    override suspend fun peoplePersonIdDelete(personId: Long): ResponseEntity<Unit> {
        val callerId = SpringSecurityContext.principal()
        val targetId = User.Id(personId.toInt())
        if (callerId != targetId) {
            throw AuthorizationException("caller $callerId can't delete $targetId")
        }

        personService.delete(targetId)

        return ResponseEntity.ok(Unit)
    }

    override suspend fun peoplePersonIdGet(personId: Long): ResponseEntity<PersonVariantMessage> =
        personService.getById(User.Id(personId.toInt()))
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
            PersonStatusMessage.draft -> personService.edit(model)
            PersonStatusMessage.ready -> personService.save(model)
        }

        return ResponseEntity.ok(Unit)
    }

    override suspend fun peoplePersonIdInterestsTopicIdDelete(
        personId: Long,
        topicId: Long,
    ): ResponseEntity<Unit> {
        interestService.remove(User.Id(personId.toInt()), Topic.Id(topicId.toInt()))
        return ResponseEntity.ok(Unit)
    }

    override suspend fun peoplePersonIdInterestsTopicIdPut(
        personId: Long,
        topicId: Long,
        interestPatchMessage: InterestPatchMessage,
    ): ResponseEntity<Unit> {
        interestService.insert(
            id = User.Id(personId.toInt()),
            interest = Person.Interest(
                topicId = Topic.Id(topicId.toInt()),
                degree = interestPatchMessage.level.value.toInt(),
            )
        )
        return ResponseEntity.ok(Unit)
    }

    override suspend fun peoplePersonIdPhotosPost(
        personId: Long,
        body: Resource?,
    ): ResponseEntity<PictureMessage> {
        require(body != null)

        val callerId = SpringSecurityContext.principal()
        val targetId = User.Id(personId.toInt())
        if (callerId != targetId) {
            throw AuthorizationException("caller $callerId can't post photo to $targetId profile")
        }

        val pictureId = Picture.Draft(
            ownerId = targetId,
            content = Picture.Content(body.contentAsByteArray),
        ).let { pictureService.save(it) }

        return PictureMessage(id = pictureId.number.toLong()).let { ResponseEntity.ok(it) }
    }

    override suspend fun peoplePersonIdPhotosPictureIdGet(
        personId: Long,
        pictureId: Long,
    ): ResponseEntity<Resource> {
        val picture = pictureService.getById(Picture.Id(pictureId.toInt()))
        return ByteArrayResource(picture.bytes).let { ResponseEntity.ok(it) }
    }

    override suspend fun peoplePersonIdPhotosPictureIdDelete(
        personId: Long,
        pictureId: Long,
    ): ResponseEntity<Unit> {
        val callerId = SpringSecurityContext.principal()
        val targetId = User.Id(personId.toInt())
        if (callerId != targetId) {
            throw AuthorizationException("caller $callerId can't remove photo of $targetId profile")
        }

        pictureService.remove(Picture.Id(pictureId.toInt()))

        return ResponseEntity.ok(Unit)
    }
}
