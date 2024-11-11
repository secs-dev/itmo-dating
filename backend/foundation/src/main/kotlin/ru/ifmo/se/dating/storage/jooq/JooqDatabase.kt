package ru.ifmo.se.dating.storage.jooq

import kotlinx.coroutines.flow.Flow
import org.jooq.DSLContext
import org.jooq.Publisher

typealias DSLBlock<T> = DSLContext.() -> Publisher<T>

internal interface JooqDatabase {
    fun <T : Any> flow(block: DSLBlock<T>): Flow<T>
    suspend fun <T : Any> only(block: DSLBlock<T>): T
    suspend fun <T : Any> maybe(block: DSLBlock<T>): T?
}
