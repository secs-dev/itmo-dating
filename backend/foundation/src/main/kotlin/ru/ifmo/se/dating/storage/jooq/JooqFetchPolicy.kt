package ru.ifmo.se.dating.storage.jooq

import org.jooq.Record
import org.jooq.SelectConditionStep
import ru.ifmo.se.dating.storage.FetchPolicy

fun <R : Record> SelectConditionStep<R>.withPolicy(policy: FetchPolicy) =
    when (policy) {
        FetchPolicy.SNAPSHOT -> this
        FetchPolicy.WRITE_LOCKED -> this.forUpdate()
    }
