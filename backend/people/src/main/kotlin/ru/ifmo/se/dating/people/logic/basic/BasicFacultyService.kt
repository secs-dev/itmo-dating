package ru.ifmo.se.dating.people.logic.basic

import kotlinx.coroutines.flow.Flow
import ru.ifmo.se.dating.people.logic.FacultyService
import ru.ifmo.se.dating.people.model.Faculty
import ru.ifmo.se.dating.people.storage.FacultyStorage

class BasicFacultyService(private val storage: FacultyStorage) : FacultyService {
    override suspend fun create(draft: Faculty.Draft): Faculty =
        storage.insert(draft)

    override suspend fun getById(id: Faculty.Id): Faculty? =
        storage.selectById(id)

    override suspend fun getAll(): Flow<Faculty> =
        storage.selectAll()
}
