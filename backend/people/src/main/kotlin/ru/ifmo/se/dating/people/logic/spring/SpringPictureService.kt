package ru.ifmo.se.dating.people.logic.spring

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.people.logic.PictureService
import ru.ifmo.se.dating.people.logic.basic.BasicPictureService
import ru.ifmo.se.dating.people.logic.logging.LoggingPictureService
import ru.ifmo.se.dating.people.storage.PictureContentStorage
import ru.ifmo.se.dating.people.storage.PictureRecordStorage

@Service
class SpringPictureService(
    recordStorage: PictureRecordStorage,
    contentStorage: PictureContentStorage,
) : PictureService by
LoggingPictureService(
    BasicPictureService(
        recordStorage = recordStorage,
        contentStorage = contentStorage,
    )
)
