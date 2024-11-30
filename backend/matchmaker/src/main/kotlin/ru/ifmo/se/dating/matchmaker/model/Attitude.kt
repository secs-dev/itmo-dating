package ru.ifmo.se.dating.matchmaker.model

import ru.ifmo.se.dating.security.auth.User

data class Attitude(
    val sourceId: User.Id,
    val targetId: User.Id,
    val kind: Kind,
) {
    enum class Kind {
        LIKE,
        SKIP,
    }
}
