package ru.ifmo.se.dating.spring

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import org.springframework.transaction.reactive.transactional
import org.springframework.transaction.support.DefaultTransactionDefinition
import ru.ifmo.se.dating.storage.TxEnv

@Component
class SpringTxEnv(transactionManager: ReactiveTransactionManager) : TxEnv {
    private val operator = DefaultTransactionDefinition().apply {
        isolationLevel = TransactionDefinition.ISOLATION_SERIALIZABLE
    }.let { TransactionalOperator.create(transactionManager, it) }

    override suspend fun <T : Any> transactional(action: suspend () -> T): T =
        operator.executeAndAwait { action() }

    override fun <T : Any> transactional(flow: Flow<T>): Flow<T> =
        flow.transactional(operator)
}
