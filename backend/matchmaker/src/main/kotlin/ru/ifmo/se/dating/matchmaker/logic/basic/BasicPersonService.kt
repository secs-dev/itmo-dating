package ru.ifmo.se.dating.matchmaker.logic.basic

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.matchmaker.logic.PersonService
import ru.ifmo.se.dating.matchmaker.model.PersonUpdate
import ru.ifmo.se.dating.matchmaker.storage.PersonStorage
import ru.ifmo.se.dating.storage.TxEnv

@Service
class BasicPersonService(
    private val storage: PersonStorage,
    private val txEnv: TxEnv,
) : PersonService {
    override suspend fun account(update: PersonUpdate) = txEnv.transactional {
        val current = storage.selectById(update.id)
        if (current == null || current.version < update.version) {
            storage.upsert(update)
        }
    }
}
