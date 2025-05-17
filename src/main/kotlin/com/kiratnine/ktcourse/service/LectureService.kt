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
import com.kiratnine.ktcourse.service.s3.ProfileS3Service
import com.kiratnine.ktcourse.translator.service.TranslatorService
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class LectureService(
    private val semesterRepository: SemesterRepository,
    private val lectureRepository: LectureRepository,
    private val profileRepository: ProfileRepository,
    private val profileMinioService: ProfileS3Service,
    private val translatorService: TranslatorService,
) {
    fun getLectures(lang: String): List<LectureDto> {
        return lectureRepository.findAllByOrderByDateAsc()
            .map { lectureToDto(it, lang) }
    }

    fun getLecturesBySemesterId(semesterId: Long, lang: String): List<LectureDto> {
        return lectureRepository.findAllBySemesterOrderByDateAsc(
            semesterRepository.findById(semesterId).orElseThrow()
        ).map { lectureToDto(it, lang) }
    }

    fun getLectureById(id: Long, lang: String): LectureDto =
        lectureToDto(lectureRepository.findById(id).orElseThrow(), lang)

    fun createLecture(input: NewLectureInputDto): Long {
        validateLectureActions()
        return lectureRepository.save(input.toModel(translatorService)).id!!
    }

    fun deleteLectureById(id: Long) {
        validateLectureActions()
        val lecture = lectureRepository.findById(id).orElseThrow()
        lecture.removeProfiles()
        lectureRepository.save(lecture)
        lectureRepository.deleteById(id)
    }

    fun replaceProfiles(id: Long, input: List<String>) {
        validateLectureActions()
        val lecture = lectureRepository.findById(id).orElseThrow()
        lecture.updateProfiles(profileRepository.findAllByLoginIn(input))
        lectureRepository.save(lecture)
    }

    fun replacePresentation(id: Long, presentationId: String) {
        validateLectureActions()
        val lecture = lectureRepository.findById(id).orElseThrow()
        lecture.presentationId = presentationId
        lectureRepository.save(lecture)
    }

    private fun lectureToDto(lecture: Lecture, lang: String): LectureDto =
        lecture.toDto(getAvatarsMap(lecture), lang)

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