package ru.ifmo.se.dating.people.logic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.model.Faculty

interface FacultyService {
    suspend fun create(draft: Faculty.Draft): Faculty
    suspend fun getById(id: Faculty.Id): Faculty?
    suspend fun getAll(): Flow<Faculty>
}
