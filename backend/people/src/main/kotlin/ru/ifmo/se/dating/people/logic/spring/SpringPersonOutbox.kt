package ru.ifmo.se.dating.people.logic.spring

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.ifmo.se.dating.logic.LoggingTransactionalOutbox
import ru.ifmo.se.dating.people.external.MatchmakerApi
import ru.ifmo.se.dating.people.logic.PersonOutbox
import ru.ifmo.se.dating.people.logic.basic.BasicPersonOutbox
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.people.storage.PersonStorage
import ru.ifmo.se.dating.security.auth.User
import ru.ifmo.se.dating.storage.TxEnv
import java.util.concurrent.TimeUnit

@Service
class SpringPersonOutbox(
    storage: PersonStorage,
    matchmaker: MatchmakerApi,
    tx: TxEnv,
) : PersonOutbox() {
    private val origin = LoggingTransactionalOutbox(
        BasicPersonOutbox(
            storage = storage,
            matchmaker = matchmaker,
            tx = tx,
        )
    )
    override val tx: TxEnv
        get() = origin.tx

    override suspend fun publishable(): Flow<User.Id> =
        origin.publishable()

    override suspend fun acquireById(id: User.Id): PersonVariant =
        origin.acquireById(id)

    override suspend fun isPublished(event: PersonVariant): Boolean =
        origin.isPublished(event)

    override suspend fun doProcess(event: PersonVariant) =
        origin.doProcess(event)

    override suspend fun markPublished(event: PersonVariant) =
        origin.markPublished(event)

    @Scheduled(
        fixedRateString = "30",
        initialDelayString = "30",
        timeUnit = TimeUnit.SECONDS,
    )
    fun doRecovery(): Unit = runBlocking {
        recover()
    }
}
