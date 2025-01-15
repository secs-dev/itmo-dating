package ru.ifmo.se.dating.people.logic.basic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import ru.ifmo.se.dating.matchmaker.client.model.generated.PersonStatusMessage
import ru.ifmo.se.dating.matchmaker.client.model.generated.PersonUpdateMessage
import ru.ifmo.se.dating.people.external.MatchmakerApi
import ru.ifmo.se.dating.people.logic.PersonOutbox
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.people.storage.PersonStorage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.FetchPolicy
import ru.ifmo.se.dating.storage.TxEnv
import java.util.concurrent.TimeUnit

class BasicPersonOutbox(
    private val storage: PersonStorage,
    private val matchmaker: MatchmakerApi,
    override val tx: TxEnv,
) : PersonOutbox() {
    override suspend fun acquireById(id: User.Id): PersonVariant =
        storage.selectById(id, FetchPolicy.WRITE_LOCKED)!!

    override suspend fun isPublished(event: PersonVariant): Boolean = when (event) {
        is Person.Draft -> false
        is Person -> event.isPublished
    }

    override suspend fun doProcess(event: PersonVariant) {
        matchmaker.putPersonUpdate(
            personId = event.id.number.toLong(),
            update = PersonUpdateMessage(
                status = when (event) {
                    is Person.Draft -> PersonStatusMessage.hidden
                    is Person -> PersonStatusMessage.active
                },
                version = event.version.number,
            ),
        )
    }

    override suspend fun markPublished(event: PersonVariant) =
        storage.setIsPublished(event.id, true)

    override fun publishable(): Flow<User.Id> =
        storage.selectNotSentIds(limit = 64)

    @Scheduled(
        fixedRateString = "30",
        initialDelayString = "30",
        timeUnit = TimeUnit.SECONDS,
    )
    fun doRecovery(): Unit = runBlocking {
        recover()
    }
}
