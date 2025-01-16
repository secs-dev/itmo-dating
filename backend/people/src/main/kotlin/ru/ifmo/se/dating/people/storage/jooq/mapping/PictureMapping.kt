package ru.ifmo.se.dating.people.storage.jooq.mapping

import org.jooq.generated.tables.records.PictureRecord
import ru.ifmo.se.dating.people.model.Picture
import ru.ifmo.se.dating.security.auth.User

fun PictureRecord.toModel(): Picture =
    Picture(
        id = Picture.Id(id!!),
        ownerId = User.Id(ownerId),
        isReferenced = isReferenced,
    )
