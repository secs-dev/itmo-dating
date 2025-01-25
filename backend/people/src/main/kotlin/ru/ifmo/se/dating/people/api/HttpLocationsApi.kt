package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.people.api.generated.LocationsApiDelegate
import ru.ifmo.se.dating.people.api.mapping.toMessage
import ru.ifmo.se.dating.people.logic.LocationService
import ru.ifmo.se.dating.people.model.generated.LocationMessage

@Controller
class HttpLocationsApi(private val service: LocationService) : LocationsApiDelegate {
    override fun locationsGet(): ResponseEntity<Flow<LocationMessage>> = runBlocking {
        service.getAll().map { it.toMessage() }
            .let { ResponseEntity.ok(it) }
    }
}
