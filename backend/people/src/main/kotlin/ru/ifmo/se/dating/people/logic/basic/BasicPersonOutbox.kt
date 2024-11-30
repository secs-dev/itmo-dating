package ru.ifmo.se.dating.people.logic.basic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.logging.Log
import ru.ifmo.se.dating.people.logic.PersonOutbox
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.storage.PersonStorage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.FetchPolicy
import ru.ifmo.se.dating.storage.TxEnv
import java.util.concurrent.TimeUnit

@Service
class BasicPersonOutbox(
    private val storage: PersonStorage,
    override val tx: TxEnv,
) : PersonOutbox() {
    private val log = Log.forClass(javaClass)

    override suspend fun acquireById(id: User.Id): Person =
        storage.selectById(id, FetchPolicy.WRITE_LOCKED) as Person

    override suspend fun isPublished(event: Person): Boolean =
        event.isPublished

    override suspend fun doProcess(event: Person) = buildString {
        append("Processing a person with id ${event.id}: ")
        append("'${event.firstName} ${event.lastName}'...")
    }.let { log.info(it) }

    override suspend fun markPublished(event: Person) =
        storage.markSent(event.id)

    override fun publishable(): Flow<User.Id> =
        storage.selectNotSentIds(limit = 64)

    @Scheduled(
        fixedRateString = "30",
        initialDelayString = "30",
        timeUnit = TimeUnit.SECONDS,
    )
    fun doRecovery(): Unit = runBlocking { recover() }
}