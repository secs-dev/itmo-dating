package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.exception.orNotFound
import ru.ifmo.se.dating.people.api.generated.PeopleApiDelegate
import ru.ifmo.se.dating.people.logic.PersonService
import ru.ifmo.se.dating.people.logic.TopicService
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.Topic
import ru.ifmo.se.dating.people.model.generated.*
import java.net.URI
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

@Controller
internal class HttpPeopleApi(
    private val people: PersonService,
    private val topics: TopicService,
) : PeopleApiDelegate {
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

    override suspend fun peoplePersonIdGet(personId: Long): ResponseEntity<PersonMessage> {
        val person = people.getById(Person.Id(personId.toInt()))
            .orNotFound("Person with id $personId not found")
        val topics = topics.getByIds(person.interests.map { it.topicId }.toSet()).associateBy { it.id }
        return ResponseEntity.ok(person.toMessage(topics))
    }

    override suspend fun peoplePersonIdPatch(
        personId: Long,
        personDraftMessage: PersonDraftMessage,
    ): ResponseEntity<Unit> = ResponseEntityStub.create()

    override suspend fun peoplePost(
        idempotencyKey: UUID,
        personDraftMessage: PersonDraftMessage,
    ): ResponseEntity<PersonMessage> {

    }

    private fun Person.toMessage(topics: Map<Topic.Id, Topic>) = PersonMessage(
        id = id.number.toLong(),
        firstName = firstName.text,
        lastName = lastName.text,
        pictures = emptySet(),
        interests = interests.map { it.toMessage(topics) }.toSet(),
        updateMoment = OffsetDateTime.now(),
    )

    private fun Person.Interest.toMessage(topics: Map<Topic.Id, Topic>) = InterestMessage(
        topic = TopicMessage(
            id = topicId.number.toLong(),
            name = topics[topicId]?.name!!,
            icon = PictureMessage(
                id = 1,
                small = URI("https://avatars.githubusercontent.com/u/53015676?v=4"),
            ),
            color = "#0000FF",
        ),
        level = rank,
    )
}
