package ru.ifmo.se.dating.people.storage.minio

import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import io.minio.errors.ErrorResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.http.entity.ContentType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.exception.NotFoundException
import ru.ifmo.se.dating.people.model.Picture
import ru.ifmo.se.dating.people.storage.PictureContentStorage
import ru.ifmo.se.dating.people.storage.minio.exception.NoSuchKeyException
import ru.ifmo.se.dating.people.storage.minio.exception.UnknownException
import java.io.ByteArrayInputStream

@Repository
class MinioPictureContentStorage(
    private val minio: MinioClient,

    @Value("\${storage.s3.bucket.profile-photos}")
    private val bucket: String,
) : PictureContentStorage {
    private val contentType = ContentType.IMAGE_JPEG

    override suspend fun upload(id: Picture.Id, content: Picture.Content): Unit = wrapped {
        PutObjectArgs.builder()
            .bucket(bucket)
            .`object`(objectName(id))
            .stream(
                ByteArrayInputStream(content.bytes),
                content.bytes.size.toLong(),
                -1, // partSize
            )
            .contentType(contentType.mimeType)
            .build()
            .let { minio.putObject(it) }
    }

    override suspend fun download(id: Picture.Id): Picture.Content = try {
        wrapped {
            GetObjectArgs.builder()
                .bucket(bucket)
                .`object`(objectName(id))
                .build()
                .let { minio.getObject(it) }
                .use { it.readBytes() }
                .let { Picture.Content(it) }
        }
    } catch (e: NoSuchKeyException) {
        throw NotFoundException(e.message!!, e)
    }

    override suspend fun remove(id: Picture.Id) = wrapped {
        RemoveObjectArgs.builder()
            .bucket(bucket)
            .`object`(objectName(id))
            .build()
            .let { minio.removeObject(it) }
    }

    private fun objectName(id: Picture.Id) = "$id.jpg"

    @Suppress("UseIfInsteadOfWhen")
    private suspend fun <T> wrapped(action: () -> T) = try {
        withContext(Dispatchers.IO) { action() }
    } catch (e: ErrorResponseException) {
        val bucketName: String? = e.errorResponse().bucketName()
        val objectName: String? = e.errorResponse().objectName()
        when (e.errorResponse().code()) {
            "NoSuchKey" -> {
                throw NoSuchKeyException("Key (bucket: $bucketName, object: $objectName) not found")
            }

            else -> {
                throw UnknownException("Unknown error: ${e.errorResponse().message()}", e)
            }
        }
    }
}
