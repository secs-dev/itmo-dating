package ru.ifmo.se.dating.people.logic.spring

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.people.logic.LocationService
import ru.ifmo.se.dating.people.logic.basic.BasicLocationService
import ru.ifmo.se.dating.people.logic.logging.LoggingLocationService
import ru.ifmo.se.dating.people.storage.LocationStorage

@Service
class SpringLocationService(
    storage: LocationStorage,
) : LocationService by
LoggingLocationService(
    BasicLocationService(
        storage = storage,
    )
)
