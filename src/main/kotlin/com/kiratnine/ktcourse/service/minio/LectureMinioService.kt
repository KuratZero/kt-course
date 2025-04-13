package com.kiratnine.ktcourse.service.minio

import com.kiratnine.ktcourse.model.Lecture
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class LectureMinioService(
    private val minioService: MinioService,
) {
    fun uploadPresentation(lecture: Lecture, presentation: MultipartFile): String {
        val objectName = "$PRESENTATION_PREFIX${lecture.id!!}-${presentation.originalFilename}"

        minioService.putObject(objectName, presentation)

        return objectName
    }

    fun getPresentationUrlOrNull(lecture: Lecture): String? {
        return minioService.getObjectUrl(lecture.presentationId ?: return null)
    }

    fun uploadImage(lecture: Lecture, image: MultipartFile): String {
        val objectName = "$IMAGE_PREFIX${lecture.id!!}-${image.originalFilename}"

        minioService.putObject(objectName, image)

        return objectName
    }

    fun getImageUrlOrNull(lecture: Lecture): String? {
        return minioService.getObjectUrl(lecture.imageId ?: return null)
    }

    private companion object {
        const val LECTURE_PREFIX = "lecture/"
        const val PRESENTATION_PREFIX = LECTURE_PREFIX + "presentation/"
        const val IMAGE_PREFIX = LECTURE_PREFIX + "image/"
    }
}