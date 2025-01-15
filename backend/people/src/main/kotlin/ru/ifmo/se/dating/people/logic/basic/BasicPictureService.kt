package ru.ifmo.se.dating.people.logic.basic

import org.springframework.stereotype.Service
import ru.ifmo.se.dating.people.logic.PictureService
import ru.ifmo.se.dating.people.model.Picture
import ru.ifmo.se.dating.people.storage.PictureContentStorage
import ru.ifmo.se.dating.people.storage.PictureRecordStorage

@Service
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
}
