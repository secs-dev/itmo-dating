package ru.ifmo.se.dating.people.logic.basic

import ru.ifmo.se.dating.people.logic.PictureService
import ru.ifmo.se.dating.people.model.Picture
import ru.ifmo.se.dating.people.storage.PictureContentStorage
import ru.ifmo.se.dating.people.storage.PictureRecordStorage

class BasicPictureService(
    private val recordStorage: PictureRecordStorage,
    private val contentStorage: PictureContentStorage,
) : PictureService {
    override suspend fun getById(id: Picture.Id): Picture.Content =
        contentStorage.download(id)

    override suspend fun save(content: Picture.Content): Picture.Id {
        val picture = recordStorage.insert()
        contentStorage.upload(picture.id, content)
        recordStorage.setIsReferenced(picture.id, isReferenced = true)
        return picture.id
    }

    override suspend fun remove(id: Picture.Id) {
        recordStorage.setIsReferenced(id, isReferenced = false)
        contentStorage.remove(id)
        recordStorage.delete(id)
    }
}
