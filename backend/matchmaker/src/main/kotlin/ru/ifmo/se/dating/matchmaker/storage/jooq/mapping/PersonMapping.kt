package ru.ifmo.se.dating.matchmaker.storage.jooq.mapping

import org.jooq.generated.tables.records.PersonRecord
import ru.ifmo.se.dating.matchmaker.model.PersonUpdate
import ru.ifmo.se.dating.security.auth.User

fun PersonRecord.toModel() = PersonUpdate(
    id = User.Id(accountId),
    status = if (isActive) {
        PersonUpdate.Status.ACTIVE
    } else {
        PersonUpdate.Status.HIDDEN
    },
    version = PersonUpdate.Version(version),
)
