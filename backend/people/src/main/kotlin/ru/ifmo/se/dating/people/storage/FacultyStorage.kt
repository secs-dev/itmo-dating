package ru.ifmo.se.dating.people.storage

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.model.Faculty

interface FacultyStorage {
    suspend fun insert(draft: Faculty.Draft): Faculty
    suspend fun selectById(id: Faculty.Id): Faculty?
    fun selectAll(): Flow<Faculty>
}
