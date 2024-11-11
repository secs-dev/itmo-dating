package ru.ifmo.se.dating.spring

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import org.springframework.transaction.reactive.transactional
import ru.ifmo.se.dating.storage.TxEnv

@Component
class SpringTxEnv(transactionManager: ReactiveTransactionManager) : TxEnv {
    private val operator = TransactionalOperator.create(transactionManager)

    override suspend fun <T : Any> transactional(action: suspend () -> T): T =
        operator.executeAndAwait { action() }

    override fun <T : Any> transactional(flow: Flow<T>): Flow<T> =
        flow.transactional(operator)
}
