package com.kiratnine.ktcourse.service.s3

import com.kiratnine.ktcourse.config.S3Config
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class S3Service {
    private val BUCKET = "ktcourse.ru"

    fun deleteObject(objectName: String) {
        val deleteRequest = DeleteObjectRequest.builder()
            .bucket(BUCKET)
            .key(objectName)
            .build()

        S3Config.s3Client.deleteObject(deleteRequest)
    }

    fun putObject(objectName: String, file: MultipartFile) {
        val putRequest = PutObjectRequest.builder()
            .bucket(BUCKET)
            .key(objectName)
            .contentType(file.contentType)
            .build()

        S3Config.s3Client.putObject(putRequest, file.inputStream.readAllBytes().let {
            RequestBody.fromBytes(it)
        })
    }

    fun getObjectUrl(objectName: String): String {
        return "https://storage.yandexcloud.net/$BUCKET/$objectName"
    }
}