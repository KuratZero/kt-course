package com.kiratnine.ktcourse.service

import com.kiratnine.ktcourse.dto.lecture.LectureDto
import com.kiratnine.ktcourse.exception.BadRequestException
import com.kiratnine.ktcourse.model.Profile
import com.kiratnine.ktcourse.model.Role
import com.kiratnine.ktcourse.repository.LectureRepository
import com.kiratnine.ktcourse.repository.ProfileRepository
import com.kiratnine.ktcourse.security.CurrentUser
import org.springframework.stereotype.Service

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class FavoriteService(
    private val profileRepository: ProfileRepository,
    private val lectureRepository: LectureRepository,
    private val lectureService: LectureService,
) {
    fun getFavoriteLectures(profileLogin: String, lang: String): Collection<LectureDto> {
        val user = getUser()
        if (user.role == Role.ADMIN || user.login == profileLogin) {
            return (profileRepository.findByLogin(profileLogin)?.favoriteLectures ?: emptyList())
                .map { lectureService.lectureToDto(it, lang) }
        }
        throw BadRequestException("Username or role not allowed")
    }

    fun addFavoriteLecture(lectureId: Long) {
        val user = getUser()
        user.favoriteLectures.add(lectureRepository.findById(lectureId).orElseThrow())
        profileRepository.save(user)
    }

    fun removeFavoriteLecture(lectureId: Long) {
        val user = getUser()
        user.favoriteLectures.remove(lectureRepository.findById(lectureId).orElseThrow())
        profileRepository.save(user)
    }

    private fun getUser(): Profile =
        profileRepository.findByLogin(CurrentUser.login())
            ?: throw NoSuchElementException("Not found profile")
}