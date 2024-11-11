package ru.ifmo.se.dating.spring

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.jooq.Publisher
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.exception.IntegrityConstraintViolationException
import org.jooq.impl.DSL
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import ru.ifmo.se.dating.storage.jooq.DSLBlock
import ru.ifmo.se.dating.storage.jooq.JooqDatabase
import ru.ifmo.se.dating.storage.jooq.exception.toStorage

@Component
class SpringJooqDatabase(private val database: DatabaseClient) : JooqDatabase {
    private val settings = Settings()
        .withBindOffsetDateTimeType(true)
        .withBindOffsetTimeType(true)

    override fun <T : Any> flow(block: DSLBlock<T>): Flow<T> =
        flux(block).asFlow()

    override suspend fun <T : Any> only(block: DSLBlock<T>): T =
        mono(block).awaitSingle()

    override suspend fun <T : Any> maybe(block: DSLBlock<T>): T? =
        mono(block).awaitSingleOrNull()

    private fun Connection.dsl() =
        DSL.using(this, SQLDialect.POSTGRES, settings)

    private fun <T : Any> flux(block: DSLBlock<T>) = database
        .inConnectionMany { block(it.dsl()).toFlux() }
        .onErrorMap(IntegrityConstraintViolationException::class.java) { it.toStorage() }

    private fun <T : Any> mono(block: DSLBlock<T>) = database
        .inConnection { block(it.dsl()).toMono() }
        .onErrorMap(IntegrityConstraintViolationException::class.java) { it.toStorage() }
}

private fun <T> publisher(jooq: Publisher<T>) =
    jooq as org.reactivestreams.Publisher<T>

private fun <T> Publisher<T>.toFlux(): Flux<T> =
    Flux.from(publisher(this))

private fun <T> Publisher<T>.toMono(): Mono<T> =
    Mono.from(publisher(this))
