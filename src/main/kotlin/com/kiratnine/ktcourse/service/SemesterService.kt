package com.kiratnine.ktcourse.service

import com.kiratnine.ktcourse.dto.semester.NewSemesterInputDto
import com.kiratnine.ktcourse.dto.semester.SemesterDto
import com.kiratnine.ktcourse.exception.BadRequestException
import com.kiratnine.ktcourse.mapper.semester.toDto
import com.kiratnine.ktcourse.mapper.semester.toModel
import com.kiratnine.ktcourse.model.Role
import com.kiratnine.ktcourse.model.Semester
import com.kiratnine.ktcourse.repository.LectureRepository
import com.kiratnine.ktcourse.repository.ProfileRepository
import com.kiratnine.ktcourse.repository.SemesterRepository
import com.kiratnine.ktcourse.security.CurrentUser
import com.kiratnine.ktcourse.translator.service.TranslatorService
import org.springframework.stereotype.Service

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class SemesterService(
    private val semesterRepository: SemesterRepository,
    private val lectureService: LectureService,
    private val profileRepository: ProfileRepository,
    private val lectureRepository: LectureRepository,
    private val translateService: TranslatorService,
) {
    fun getSemesters(lang: String): List<SemesterDto> =
        semesterRepository.findAllByOrderByPositionAsc()
            .map {
                it.toDto(
                    lectureService.getLecturesBySemesterId(it.id!!, lang),
                    lang,
                    computePercentOfView(it)
                )
            }

    fun getSemester(id: Long, lang: String): SemesterDto {
        val semester = semesterRepository.findById(id).orElseThrow()
        return semester.toDto(
            lectureService.getLecturesBySemesterId(id, lang),
            lang,
            computePercentOfView(semester)
        )
    }

    fun createSemester(input: NewSemesterInputDto): Long {
        validateSemesterActions()
        return semesterRepository.save(input.toModel(translateService)).id!!
    }

    fun replaceLectures(id: Long, lectureIds: List<Long>) {
        validateSemesterActions()
        val semester = semesterRepository.findById(id).orElseThrow()
        semester.updateTopics(
            lectureRepository.findAllByIdIn(lectureIds)
        )
        semesterRepository.save(semester)
    }

    fun deleteSemester(id: Long) {
        validateSemesterActions()
        val semester = semesterRepository.findById(id).orElseThrow()
        semester.lectures.forEach { it.removeCreators() }
        semesterRepository.save(semester)
        semesterRepository.deleteById(id)
    }

    private fun validateSemesterActions() {
        if (profileRepository.findByLogin(CurrentUser.login())?.role == Role.ADMIN) {
            return
        } else {
            throw BadRequestException("Do not allow semester actions")
        }
    }

    private fun computePercentOfView(semester: Semester): Double? {
        val user = profileRepository.findByLogin(CurrentUser.login()) ?: return null
        return semester.lectures.count { user.viewedLectures.contains(it) }
            .toDouble() / semester.lectures.size.toDouble()
    }
}