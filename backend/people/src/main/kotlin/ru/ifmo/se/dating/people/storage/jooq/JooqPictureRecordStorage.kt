package ru.ifmo.se.dating.people.storage.jooq

import org.jooq.generated.tables.references.PICTURE
import ru.ifmo.se.dating.people.model.Picture
import ru.ifmo.se.dating.people.storage.PictureRecordStorage
import ru.ifmo.se.dating.people.storage.jooq.mapping.toModel
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

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

    override suspend fun delete(id: Picture.Id) = database.only {
        delete(PICTURE)
            .where(PICTURE.ID.eq(id.number))
    }.let { }
}
