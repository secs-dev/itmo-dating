package ru.ifmo.se.dating.people.storage.jooq

import org.jooq.generated.tables.references.PICTURE
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.people.model.Picture
import ru.ifmo.se.dating.people.storage.PictureRecordStorage
import ru.ifmo.se.dating.people.storage.jooq.mapping.toModel
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class JooqPictureRecordStorage(
    private val database: JooqDatabase,
) : PictureRecordStorage {
    override suspend fun insert(): Picture = database.only {
        insertInto(PICTURE)
            .set(PICTURE.IS_REFERENCED, false)
            .returning()
    }.toModel()

    override suspend fun setIsReferenced(id: Picture.Id, isReferenced: Boolean) = database.only {
        update(PICTURE)
            .set(PICTURE.IS_REFERENCED, isReferenced)
            .where(PICTURE.ID.eq(id.number))
    }.let { }
}
