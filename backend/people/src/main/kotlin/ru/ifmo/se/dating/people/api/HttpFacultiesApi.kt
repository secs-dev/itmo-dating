package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.people.api.generated.FacultiesApiDelegate
import ru.ifmo.se.dating.people.logic.FacultyService
import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.model.generated.FacultyMessage

@Controller
class HttpFacultiesApi(private val service: FacultyService) : FacultiesApiDelegate {
    override fun facultiesGet(): ResponseEntity<Flow<FacultyMessage>> =
        service.getAll().map { it.toMessage() }
            .let { ResponseEntity.ok(it) }

    companion object {
        fun Faculty.toMessage() = FacultyMessage(
            id = id.number.toLong(),
            longName = longName,
        )
    }
}
