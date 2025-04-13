package com.kiratnine.ktcourse.service

import com.kiratnine.ktcourse.dto.lecture.LectureDto
import com.kiratnine.ktcourse.dto.lecture.NewLectureInputDto
import com.kiratnine.ktcourse.exception.BadRequestException
import com.kiratnine.ktcourse.mapper.lecture.toDto
import com.kiratnine.ktcourse.mapper.lecture.toModel
import com.kiratnine.ktcourse.model.Lecture
import com.kiratnine.ktcourse.model.Role
import com.kiratnine.ktcourse.repository.LectureRepository
import com.kiratnine.ktcourse.repository.ProfileRepository
import com.kiratnine.ktcourse.repository.SemesterRepository
import com.kiratnine.ktcourse.security.CurrentUser
import com.kiratnine.ktcourse.service.minio.LectureMinioService
import com.kiratnine.ktcourse.service.minio.ProfileMinioService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.stream.Collectors

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class LectureService(
    private val semesterRepository: SemesterRepository,
    private val lectureRepository: LectureRepository,
    private val profileRepository: ProfileRepository,
    private val profileMinioService: ProfileMinioService,
    private val lectureMinioService: LectureMinioService
) {
    fun getLectures(): List<LectureDto> {
        return lectureRepository.findAllByOrderByDateAsc()
            .map(this::lectureToDto)
    }

    fun getLecturesBySemesterId(semesterId: Long): List<LectureDto> {
        return lectureRepository.findAllBySemesterOrderByDateAsc(
            semesterRepository.findById(semesterId).orElseThrow()
        ).map(this::lectureToDto)
    }

    fun getLectureBySlug(slug: String): LectureDto =
        lectureToDto(lectureRepository.findBySlug(slug).orElseThrow())

    fun createLecture(input: NewLectureInputDto) {
        validateLectureActions()
        lectureRepository.save(input.toModel())
    }

    fun deleteLectureBySlug(slug: String) {
        validateLectureActions()
        val lecture = lectureRepository.findBySlug(slug).orElseThrow()
        lecture.removeProfiles()
        lectureRepository.save(lecture)
        lectureRepository.deleteBySlug(slug)
    }

    fun replaceProfiles(slug: String, input: List<String>) {
        validateLectureActions()
        val lecture = lectureRepository.findBySlug(slug).orElseThrow()
        lecture.updateProfiles(profileRepository.findAllByLoginIn(input))
        lectureRepository.save(lecture)
    }

    fun replaceImage(slug: String, image: MultipartFile) {
        validateLectureActions()
        val lecture = lectureRepository.findBySlug(slug).orElseThrow()
        lecture.imageId = lectureMinioService.uploadImage(lecture, image)
        lectureRepository.save(lecture)
    }

    fun replacePresentation(slug: String, presentation: MultipartFile) {
        validateLectureActions()
        val lecture = lectureRepository.findBySlug(slug).orElseThrow()
        lecture.presentationId = lectureMinioService.uploadPresentation(lecture, presentation)
        lectureRepository.save(lecture)
    }

    private fun lectureToDto(lecture: Lecture): LectureDto =
        lecture.toDto(
            getAvatarsMap(lecture),
            lectureMinioService.getImageUrlOrNull(lecture),
            lectureMinioService.getPresentationUrlOrNull(lecture),
        )

    private fun getAvatarsMap(lecture: Lecture): Map<Long, String?> =
        lecture.profiles.stream()
            .filter { profile -> profile.avatarId != null }
            .collect(
                Collectors.toMap(
                    { it.id!! },
                    { profileMinioService.getAvatarUrlOrNull(it) }
                ))

    private fun validateLectureActions() {
        if (profileRepository.findByLogin(CurrentUser.login())?.role == Role.ADMIN) {
            return
        } else {
            throw BadRequestException("Do not allow lectures actions")
        }
    }
}