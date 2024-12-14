package ru.ifmo.se.dating.people.api.mapping

import ru.ifmo.se.dating.people.model.Coordinates
import ru.ifmo.se.dating.people.model.Location
import ru.ifmo.se.dating.people.model.generated.CoordinatesMessage
import ru.ifmo.se.dating.people.model.generated.LocationMessage

fun Location.toMessage() = LocationMessage(
    id = id.number.toLong(),
    name = name,
    coordinates = coordinates.toMessage(),
)

fun Coordinates.toMessage() = CoordinatesMessage(
    latitude = latitude,
    longitude = longitude,
)
