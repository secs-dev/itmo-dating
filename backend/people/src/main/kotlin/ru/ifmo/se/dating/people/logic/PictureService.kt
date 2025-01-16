package ru.ifmo.se.dating.people.logic

import ru.ifmo.se.dating.people.model.Picture

interface PictureService {
    suspend fun getById(id: Picture.Id): Picture.Content
    suspend fun save(draft: Picture.Draft): Picture.Id
    suspend fun remove(id: Picture.Id)
    suspend fun recover()
}
