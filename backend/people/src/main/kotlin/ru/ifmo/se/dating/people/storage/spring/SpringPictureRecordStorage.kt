package ru.ifmo.se.dating.people.storage.spring

import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.people.storage.PictureRecordStorage
import ru.ifmo.se.dating.people.storage.jooq.JooqPictureRecordStorage
import ru.ifmo.se.dating.people.storage.logging.LoggingPictureRecordStorage
import ru.ifmo.se.dating.storage.jooq.JooqDatabase

@Repository
class SpringPictureRecordStorage(
    database: JooqDatabase,
) : PictureRecordStorage by
LoggingPictureRecordStorage(
    JooqPictureRecordStorage(
        database = database,
    )
)
