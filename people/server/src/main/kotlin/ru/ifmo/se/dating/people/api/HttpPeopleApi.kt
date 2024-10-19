package ru.ifmo.se.dating.people.api

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.people.api.generated.PeopleApiDelegate
import ru.ifmo.se.dating.people.model.generated.Faculty
import ru.ifmo.se.dating.people.model.generated.Person
import ru.ifmo.se.dating.people.model.generated.PersonDraft
import ru.ifmo.se.dating.people.model.generated.ZodiacSign
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

@Service
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
        zodiac: List<ZodiacSign>?,
        faculty: List<Faculty>?,
        latitude: Double?,
        longitude: Double?,
        radius: Int?,
        updatedMin: OffsetDateTime?,
        updatedMax: OffsetDateTime?,
        sortBy: List<String>?,
    ): ResponseEntity<List<Person>> =
        ResponseEntityStub.create()

    override fun peoplePersonIdDelete(personId: Long): ResponseEntity<Unit> =
        ResponseEntityStub.create()

    override fun peoplePersonIdGet(personId: Long): ResponseEntity<Person> =
        ResponseEntityStub.create()

    override fun peoplePersonIdPatch(
        personId: Long,
        personDraft: PersonDraft,
    ): ResponseEntity<Unit> =
        ResponseEntityStub.create()

    override fun peoplePost(
        idempotencyKey: UUID,
        personDraft: PersonDraft,
    ): ResponseEntity<Person> =
        ResponseEntityStub.create()
}
