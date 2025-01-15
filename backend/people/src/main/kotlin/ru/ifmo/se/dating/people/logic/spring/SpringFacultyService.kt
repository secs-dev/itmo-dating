package ru.ifmo.se.dating.people.logic.spring

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.people.logic.FacultyService
import ru.ifmo.se.dating.people.logic.basic.BasicFacultyService
import ru.ifmo.se.dating.people.logic.logging.LoggingFacultyService
import ru.ifmo.se.dating.people.storage.FacultyStorage

@Service
class SpringFacultyService(
    storage: FacultyStorage,
) : FacultyService by
LoggingFacultyService(
    BasicFacultyService(
        storage = storage,
    )
)
