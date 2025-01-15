package ru.ifmo.se.dating.people.storage.minio

import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.http.entity.ContentType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.people.model.Picture
import ru.ifmo.se.dating.people.storage.PictureContentStorage
import java.io.ByteArrayInputStream

@Repository
class MinioPictureContentStorage(
    private val minio: MinioClient,

    @Value("\${storage.s3.bucket.profile-photos}")
    private val bucket: String,
) : PictureContentStorage {
    private val contentType = ContentType.IMAGE_JPEG

    override suspend fun upload(id: Picture.Id, content: Picture.Content): Unit =
        withContext(Dispatchers.IO) {
            PutObjectArgs.builder()
                .bucket(bucket)
                .`object`(objectName(id))
                .stream(
                    ByteArrayInputStream(content.bytes),
                    content.bytes.size.toLong(),
                    /* partSize = */ -1,
                )
                .contentType(contentType.mimeType)
                .build()
                .let { minio.putObject(it) }
        }

    override suspend fun download(id: Picture.Id): Picture.Content =
        withContext(Dispatchers.IO) {
            GetObjectArgs.builder()
                .bucket(bucket)
                .`object`(objectName(id))
                .build()
                .let { minio.getObject(it) }
                .use { it.readBytes() }
                .let { Picture.Content(it) }
        }

    private fun objectName(id: Picture.Id) = "$id.jpg"
}
