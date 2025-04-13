package com.kiratnine.ktcourse.controller

import com.kiratnine.ktcourse.dto.semester.NewSemesterInputDto
import com.kiratnine.ktcourse.dto.semester.SemesterDto
import com.kiratnine.ktcourse.service.SemesterService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@RestController
@RequestMapping("/api/v1")
class SemesterController(
    private val semesterService: SemesterService
) {
    @GetMapping("/semesters")
    @Operation(summary = "Ручка для главной странички")
    fun getSemesters(): List<SemesterDto> =
        semesterService.getSemesters()

    @GetMapping("/semesters/{semesterId}")
    @Operation(summary = "Ручка для странички семестра")
    fun getSemester(@PathVariable("semesterId") semesterId: Long): SemesterDto =
        semesterService.getSemester(semesterId)

    @PostMapping("/semesters")
    fun createSemester(@RequestBody request: NewSemesterInputDto) =
        semesterService.createSemester(request)

    @PatchMapping("/semesters/{semesterId}/lectures")
    fun replaceLectures(@PathVariable("semesterId") semesterId: Long, @RequestBody lectureSlugs: List<String>) =
        semesterService.replaceLectures(semesterId, lectureSlugs)

    @DeleteMapping("/semesters/{semesterId}")
    fun deleteSemester(@PathVariable("semesterId") semesterId: Long) =
        semesterService.deleteSemester(semesterId)
}