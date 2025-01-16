package ru.ifmo.se.dating.people.storage.jooq

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jooq.generated.tables.references.PICTURE
import ru.ifmo.se.dating.people.model.Picture
import ru.ifmo.se.dating.people.storage.PictureRecordStorage
import ru.ifmo.se.dating.people.storage.jooq.mapping.toModel
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.jooq.JooqDatabase
import java.time.OffsetDateTime
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

class JooqPictureRecordStorage(
    private val database: JooqDatabase,
) : PictureRecordStorage {
    override suspend fun insert(ownerId: User.Id): Picture = database.only {
        insertInto(PICTURE)
            .set(PICTURE.OWNER_ID, ownerId.number)
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

    override fun selectAbandoned(): Flow<Picture> = database.flow {
        val threshold = 5.minutes.toJavaDuration()
        selectFrom(PICTURE)
            .where(
                PICTURE.IS_REFERENCED.eq(false)
                    .and(PICTURE.CREATION_MOMENT.le(OffsetDateTime.now().minus(threshold)))
            )
    }.map { it.toModel() }

    override fun selectByOwner(
        ownerId: User.Id,
    ): Flow<Picture> = database.flow {
        selectFrom(PICTURE)
            .where(
                PICTURE.IS_REFERENCED.eq(true)
                    .and(PICTURE.OWNER_ID.eq(ownerId.number))
            )
    }.map { it.toModel() }
}
