package ru.ifmo.se.dating.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toCollection
import ru.ifmo.se.dating.storage.TxEnv

@Suppress("ComplexInterface")
interface TransactionalOutbox<E, Id> {
    val tx: TxEnv
    suspend fun publishable(): Flow<Id>
    suspend fun acquireById(id: Id): E
    suspend fun isPublished(event: E): Boolean
    suspend fun doProcess(event: E)
    suspend fun markPublished(event: E)

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
