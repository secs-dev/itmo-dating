package ru.ifmo.se.dating.matchmaker.logic.basic

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.exception.ConflictException
import ru.ifmo.se.dating.exception.InvalidValueException
import ru.ifmo.se.dating.exception.NotFoundException
import ru.ifmo.se.dating.matchmaker.logic.AttitudeService
import ru.ifmo.se.dating.matchmaker.model.Attitude
import ru.ifmo.se.dating.matchmaker.storage.AttitudeStorage
import ru.ifmo.se.dating.storage.exception.LinkViolationException
import ru.ifmo.se.dating.storage.exception.UniqueViolationException

@Service
class BasicAttitudeService(
    private val storage: AttitudeStorage,
) : AttitudeService {
    override suspend fun express(attitude: Attitude) = try {
        if (attitude.sourceId == attitude.targetId) {
            throw InvalidValueException("Can't express a reflexive attitude")
        }

        storage.insert(attitude)
    } catch (exception: UniqueViolationException) {
        throw ConflictException("An attitude was already expressed", exception)
    } catch (exception: LinkViolationException) {
        throw NotFoundException("source or target ids does not exist", exception)
    }
}
