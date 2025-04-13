package com.kiratnine.ktcourse.service.minio

import com.kiratnine.ktcourse.model.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class ProfileMinioService(
    private val minioService: MinioService
) {
    fun uploadAvatar(profile: Profile, file: MultipartFile): String {
        val objectName = "avatars/${profile.id!!}-${file.originalFilename}"

        minioService.putObject(objectName, file)

        return objectName
    }

    fun getAvatarUrlOrNull(profile: Profile): String? =
        if (profile.avatarId == null) null
        else minioService.getObjectUrl(profile.avatarId!!)
}