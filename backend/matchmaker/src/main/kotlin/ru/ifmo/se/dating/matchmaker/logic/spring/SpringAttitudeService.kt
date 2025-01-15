package ru.ifmo.se.dating.matchmaker.logic.spring

import org.springframework.stereotype.Service;
import ru.ifmo.se.dating.matchmaker.logic.AttitudeService
import ru.ifmo.se.dating.matchmaker.logic.basic.BasicAttitudeService
import ru.ifmo.se.dating.matchmaker.logic.logging.LoggingAttitudeService
import ru.ifmo.se.dating.matchmaker.storage.AttitudeStorage;

@Service
class SpringAttitudeService(
    storage: AttitudeStorage,
) : AttitudeService by
LoggingAttitudeService(
    BasicAttitudeService(storage),
)
