package com.kiratnine.ktcourse.service.s3

import com.kiratnine.ktcourse.model.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class ProfileS3Service(
    private val s3Service: S3Service
) {
    fun uploadAvatar(profile: Profile, file: MultipartFile): String {
        profile.avatarId.let { avatarId ->
            if (avatarId != null) {
                s3Service.deleteObject(avatarId)
            }
        }

        val objectName = "avatars/${profile.id!!}-${file.originalFilename}"
        s3Service.putObject(objectName, file)
        return objectName
    }

    fun getAvatarUrlOrNull(profile: Profile): String? {
        if (profile.avatarId == null) return null
        return s3Service.getObjectUrl(profile.avatarId!!)
    }
}