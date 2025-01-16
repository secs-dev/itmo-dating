package ru.ifmo.se.dating.people.model

import kotlin.math.cos
import kotlin.math.sqrt

class Area(
    val center: Coordinates,
    val radius: Double,
) {
    fun contains(coordinates: Coordinates): Boolean {
        val lat1Rad = Math.toRadians(center.latitude)
        val lat2Rad = Math.toRadians(coordinates.latitude)
        val lon1Rad = Math.toRadians(center.longitude)
        val lon2Rad = Math.toRadians(coordinates.longitude)

        val x = (lon2Rad - lon1Rad) * cos((lat1Rad + lat2Rad) / 2)
        val y = lat2Rad - lat1Rad
        val distance: Double = sqrt(x * x + y * y) * EARTH_RADIUS

        return distance <= radius
    }

    companion object {
        const val EARTH_RADIUS = 6371
    }
}
