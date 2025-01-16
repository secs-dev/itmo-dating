package ru.ifmo.se.dating.people.logic.spring

import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.people.logic.PictureService
import ru.ifmo.se.dating.people.logic.basic.BasicPictureService
import ru.ifmo.se.dating.people.logic.logging.LoggingPictureService
import ru.ifmo.se.dating.people.storage.PictureContentStorage
import ru.ifmo.se.dating.people.storage.PictureRecordStorage
import java.util.concurrent.TimeUnit

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
) {
    @Scheduled(
        fixedRateString = "30",
        initialDelayString = "30",
        timeUnit = TimeUnit.SECONDS,
    )
    fun doRecovery(): Unit = runBlocking {
        recover()
    }
}
