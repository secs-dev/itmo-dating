package ru.ifmo.se.dating.matchmaker.api.mapping

import ru.ifmo.se.dating.matchmaker.model.Attitude
import ru.ifmo.se.dating.matchmaker.model.PersonUpdate
import ru.ifmo.se.dating.matchmaker.model.generated.AttitudeKindMessage
import ru.ifmo.se.dating.matchmaker.model.generated.PersonStatusMessage
import ru.ifmo.se.dating.matchmaker.model.generated.PersonUpdateMessage
import ru.ifmo.se.dating.security.auth.User

fun PersonUpdateMessage.toModel(personId: Long) = PersonUpdate(
    id = User.Id(personId.toInt()),
    status = status.toModel(),
    version = PersonUpdate.Version(version),
)

fun PersonStatusMessage.toModel() = when (this) {
    PersonStatusMessage.hidden -> PersonUpdate.Status.HIDDEN
    PersonStatusMessage.active -> PersonUpdate.Status.ACTIVE
}

fun AttitudeKindMessage.toModel() = when (this) {
    AttitudeKindMessage.like -> Attitude.Kind.LIKE
    AttitudeKindMessage.skip -> Attitude.Kind.SKIP
}
