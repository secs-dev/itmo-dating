package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.people.api.generated.LocationsApiDelegate
import ru.ifmo.se.dating.people.logic.LocationService
import ru.ifmo.se.dating.people.model.Coordinates
import ru.ifmo.se.dating.people.model.Location
import ru.ifmo.se.dating.people.model.generated.CoordinatesMessage
import ru.ifmo.se.dating.people.model.generated.LocationMessage

@Controller
class HttpLocationsApi(private val service: LocationService) : LocationsApiDelegate {
    override fun locationsGet(): ResponseEntity<Flow<LocationMessage>> =
        service.getAll().map { it.toMessage() }
            .let { ResponseEntity.ok(it) }

    companion object {
        fun Location.toMessage() = LocationMessage(
            id = id.number.toLong(),
            name = name,
            coordinates = coordinates.toMessage(),
        )

        private fun Coordinates.toMessage() = CoordinatesMessage(
            latitude = latitude,
            longitude = longitude,
        )
    }
}
