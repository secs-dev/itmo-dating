package ru.ifmo.se.dating.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toCollection
import ru.ifmo.se.dating.storage.TxEnv

abstract class TransactionalOutbox<E, Id> {
    protected abstract val tx: TxEnv
    protected abstract fun publishable(): Flow<Id>
    protected abstract suspend fun acquireById(id: Id): E
    protected abstract suspend fun isPublished(event: E): Boolean
    protected abstract suspend fun doProcess(event: E)
    protected abstract suspend fun markPublished(event: E)

    suspend fun process(id: Id) = tx.transactional {
        val event = acquireById(id)
        if (!isPublished(event)) {
            doProcess(event)
            markPublished(event)
        }
    }

    suspend fun recover() =
        publishable()
            .toCollection(mutableListOf())
            .shuffled()
            .forEach { process(it) }
}
