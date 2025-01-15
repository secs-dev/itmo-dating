package ru.ifmo.se.dating.matchmaker.logic.spring

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.logic.PersonService
import ru.ifmo.se.dating.matchmaker.logic.basic.BasicPersonService
import ru.ifmo.se.dating.matchmaker.logic.logging.LoggingPersonService
import ru.ifmo.se.dating.matchmaker.storage.PersonStorage
import ru.ifmo.se.dating.storage.TxEnv

@Service
class SpringPersonService(
    private val storage: PersonStorage,
    private val txEnv: TxEnv,
) : PersonService by
LoggingPersonService(
    BasicPersonService(
        storage = storage,
        txEnv = txEnv,
    )
)
