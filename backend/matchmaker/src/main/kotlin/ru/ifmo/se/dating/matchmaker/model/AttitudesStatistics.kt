package ru.ifmo.se.dating.matchmaker.model

import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.validation.expectInRange

data class AttitudesStatistics(
    val userId: User.Id,
    val likes: Long,
    val skips: Long,
) {
    init {
        expectInRange("likes", likes, 0..Long.MAX_VALUE)
        expectInRange("skips", skips, 0..Long.MAX_VALUE)
    }
}
