package ru.ifmo.se.dating.matchmaker.logic

import ru.ifmo.se.dating.matchmaker.model.PersonUpdate

interface PersonService {
    suspend fun account(update: PersonUpdate)
}
