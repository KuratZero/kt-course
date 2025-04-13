package com.kiratnine.ktcourse.controller

import com.kiratnine.ktcourse.dto.lecture.LectureDto
import com.kiratnine.ktcourse.dto.lecture.NewLectureInputDto
import com.kiratnine.ktcourse.service.LectureService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@RestController
@RequestMapping("/api/v1")
class LectureController(
    private val lectureService: LectureService,
) {
    @GetMapping("/lectures")
    fun getLecturesBySemesterId(@RequestParam(required = false) semesterId: Long?): List<LectureDto> =
        if (semesterId != null) lectureService.getLecturesBySemesterId(semesterId)
        else lectureService.getLectures()

    @GetMapping("/lectures/{slug}")
    @Operation(summary = "Ручка для странички лекции")
    fun getLectureBySlug(@PathVariable("slug") slug: String): LectureDto =
        lectureService.getLectureBySlug(slug)

    @PostMapping("/lectures")
    fun createLecture(@RequestBody request: NewLectureInputDto) =
        lectureService.createLecture(request)

    @DeleteMapping("/lectures/{slug}")
    fun deleteLectureBySlug(@PathVariable("slug") slug: String) =
        lectureService.deleteLectureBySlug(slug)

    @PatchMapping("/lectures/{slug}/profiles")
    fun replaceProfiles(@PathVariable("slug") slug: String, @RequestBody logins: List<String>) =
        lectureService.replaceProfiles(slug, logins)

    @PatchMapping("/lectures/{slug}/image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun replaceImage(@PathVariable("slug") slug: String, @RequestPart image: MultipartFile) =
        lectureService.replaceImage(slug, image)

    @PatchMapping("/lectures/{slug}/presentation", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun replacePresentation(@PathVariable("slug") slug: String, @RequestPart presentation: MultipartFile) =
        lectureService.replacePresentation(slug, presentation)
}