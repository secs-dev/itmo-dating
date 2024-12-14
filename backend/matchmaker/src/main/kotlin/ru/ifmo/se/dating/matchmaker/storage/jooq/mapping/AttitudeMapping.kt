package ru.ifmo.se.dating.matchmaker.storage.jooq.mapping

import org.jooq.generated.enums.AttitudeKind
import ru.ifmo.se.dating.matchmaker.model.Attitude

fun Attitude.Kind.toRecord() = when (this) {
    Attitude.Kind.LIKE -> AttitudeKind.like
    Attitude.Kind.SKIP -> AttitudeKind.skip
}
