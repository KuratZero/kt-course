package com.kiratnine.ktcourse.service

import com.kiratnine.ktcourse.dto.semester.NewSemesterInputDto
import com.kiratnine.ktcourse.dto.semester.SemesterDto
import com.kiratnine.ktcourse.exception.BadRequestException
import com.kiratnine.ktcourse.mapper.semester.toDto
import com.kiratnine.ktcourse.mapper.semester.toModel
import com.kiratnine.ktcourse.model.Role
import com.kiratnine.ktcourse.repository.LectureRepository
import com.kiratnine.ktcourse.repository.ProfileRepository
import com.kiratnine.ktcourse.repository.SemesterRepository
import com.kiratnine.ktcourse.security.CurrentUser
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
) {
    fun getSemesters(): List<SemesterDto> =
        semesterRepository.findAllByOrderByPositionAsc()
            .map { it.toDto(lectureService.getLecturesBySemesterId(it.id!!)) }

    fun getSemester(id: Long): SemesterDto =
        semesterRepository.findById(id).orElseThrow()
            .toDto(lectureService.getLecturesBySemesterId(id))

    fun createSemester(input: NewSemesterInputDto) {
        validateSemesterActions()
        semesterRepository.save(input.toModel())
    }

    fun replaceLectures(id: Long, lectureSlugs: List<String>) {
        validateSemesterActions()
        val semester = semesterRepository.findById(id).orElseThrow()
        semester.updateTopics(
            lectureRepository.findAllBySlugIn(lectureSlugs)
        )
        semesterRepository.save(semester)
    }

    fun deleteSemester(id: Long) {
        validateSemesterActions()
        val semester = semesterRepository.findById(id).orElseThrow()
        semester.lectures.forEach { it.removeProfiles() }
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
}