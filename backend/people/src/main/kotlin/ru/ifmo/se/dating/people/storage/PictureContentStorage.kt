package ru.ifmo.se.dating.people.storage

import ru.ifmo.se.dating.people.model.Picture

interface PictureContentStorage {
    suspend fun upload(id: Picture.Id, content: Picture.Content)
    suspend fun download(id: Picture.Id): Picture.Content
    suspend fun remove(id: Picture.Id)
}
