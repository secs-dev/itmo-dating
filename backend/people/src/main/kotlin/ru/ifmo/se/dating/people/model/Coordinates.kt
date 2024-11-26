package ru.ifmo.se.dating.people.model

import ru.ifmo.se.dating.validation.expectInRange

data class Coordinates(
    val latitude: Double,
    val longitude: Double,
) {
    init {
        expectInRange("Latitude", latitude, latitudeRange)
        expectInRange("Longitude", longitude, longitudeRange)
    }

    override fun toString(): String = "($latitude, $longitude)"

    companion object {
        private val latitudeRange = -90.0..90.0
        private val longitudeRange = -180.0..180.0
    }
}
