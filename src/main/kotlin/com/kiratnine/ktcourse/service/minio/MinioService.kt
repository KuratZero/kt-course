package com.kiratnine.ktcourse.service.minio

import io.minio.GetPresignedObjectUrlArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.http.Method
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class MinioService(
    private val minioClient: MinioClient,
    @Value("\${minio.bucket}") private val bucket: String,
) {
    fun putObject(objectName: String, file: MultipartFile) =
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucket)
                .`object`(objectName)
                .stream(file.inputStream, file.size, -1)
                .contentType(file.contentType)
                .build()
        )

    fun getObjectUrl(objectName: String) =
        minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucket)
                .`object`(objectName)
                .build()
        )
}