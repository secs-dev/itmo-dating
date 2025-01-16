package ru.ifmo.se.dating.people.logic.spring

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.people.logic.InterestService
import ru.ifmo.se.dating.people.logic.basic.BasicInterestService
import ru.ifmo.se.dating.people.logic.logging.LoggingInterestService
import ru.ifmo.se.dating.people.storage.InterestStorage

@Service
class SpringInterestService(
    storage: InterestStorage,
) : InterestService by
LoggingInterestService(
    BasicInterestService(storage)
)
