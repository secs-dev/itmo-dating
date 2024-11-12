package ru.ifmo.se.dating.storage

import kotlinx.coroutines.flow.Flow

interface TxEnv {
    suspend fun <T : Any> transactional(action: suspend () -> T): T
    fun <T : Any> transactional(flow: Flow<T>): Flow<T>
}
