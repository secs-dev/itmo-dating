package ru.ifmo.se.dating.people.logic.spring

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.people.logic.PersonOutbox
import ru.ifmo.se.dating.people.logic.PersonService
import ru.ifmo.se.dating.people.logic.basic.BasicPersonService
import ru.ifmo.se.dating.people.logic.logging.LoggingPersonService
import ru.ifmo.se.dating.people.storage.PersonStorage
import ru.ifmo.se.dating.storage.TxEnv

@Service
class SpringPersonService(
    storage: PersonStorage,
    txEnv: TxEnv,
    outbox: PersonOutbox,
) : PersonService by
LoggingPersonService(
    BasicPersonService(
        storage = storage,
        txEnv = txEnv,
        outbox = outbox,
    )
)
