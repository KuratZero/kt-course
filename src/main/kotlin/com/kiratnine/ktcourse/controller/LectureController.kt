package com.kiratnine.ktcourse.controller

import com.kiratnine.ktcourse.dto.lecture.LectureDto
import com.kiratnine.ktcourse.dto.lecture.NewLectureInputDto
import com.kiratnine.ktcourse.service.LectureService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@RestController
@RequestMapping("/v1")
class LectureController(
    private val lectureService: LectureService,
) {
    @GetMapping("/lectures")
    fun getLecturesBySemesterId(
        @RequestParam(required = false) semesterId: Long?,
        @RequestParam(required = false) lang: String = "ru",
    ): List<LectureDto> =
        if (semesterId != null) lectureService.getLecturesBySemesterId(semesterId, lang)
        else lectureService.getLectures(lang)

    @GetMapping("/lectures/{id}")
    @Operation(summary = "Ручка для странички лекции")
    fun getLectureById(
        @PathVariable("id") id: Long,
        @RequestParam(required = false) lang: String = "ru",
    ): LectureDto =
        lectureService.getLectureById(id, lang)

    @PostMapping("/lectures")
    fun createLecture(@RequestBody request: NewLectureInputDto): Long =
        lectureService.createLecture(request)

    @DeleteMapping("/lectures/{id}")
    fun deleteLectureBySlug(@PathVariable("id") id: Long) =
        lectureService.deleteLectureById(id)

    @PatchMapping("/lectures/{id}/profiles")
    fun replaceProfiles(@PathVariable("id") id: Long, @RequestBody logins: List<String>) =
        lectureService.replaceProfiles(id, logins)

    @PatchMapping("/lectures/{id}/presentation")
    fun replacePresentation(@PathVariable("id") id: Long, @RequestBody presentation: String) =
        lectureService.replacePresentation(id, presentation)
}