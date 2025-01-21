package ru.ifmo.se.dating.people.storage.spring

import io.minio.MinioClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import ru.ifmo.se.dating.people.storage.PictureContentStorage
import ru.ifmo.se.dating.people.storage.logging.LoggingPictureContentStorage
import ru.ifmo.se.dating.people.storage.minio.MinioPictureContentStorage

@Repository
class SpringPictureContentStorage(
    minio: MinioClient,

    @Value("\${itmo-dating.s3.bucket.profile-photos}")
    bucket: String,
) : PictureContentStorage by
LoggingPictureContentStorage(
    MinioPictureContentStorage(
        minio = minio,
        bucket = bucket,
    )
)
