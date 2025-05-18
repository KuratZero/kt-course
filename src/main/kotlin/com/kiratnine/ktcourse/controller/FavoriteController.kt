package com.kiratnine.ktcourse.controller

import com.kiratnine.ktcourse.dto.lecture.LectureDto
import com.kiratnine.ktcourse.service.FavoriteService
import org.springframework.web.bind.annotation.GetMapping
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
class FavoriteController(
    private val favoriteService: FavoriteService,
) {
    @GetMapping("/favorites/{profileLogin}")
    fun getFavoriteProfile(
        @PathVariable("profileLogin") profileLogin: String,
        @RequestParam(required = false) lang: String = "ru",
    ): Collection<LectureDto> = favoriteService.getFavoriteLectures(profileLogin, lang)

    @PostMapping("/favorites/add")
    fun addFavorite(
        @RequestBody lectureId: Long,
    ) = favoriteService.addFavoriteLecture(lectureId)

    @PostMapping("/favorites/remove")
    fun removeFavorite(
        @RequestBody lectureId: Long,
    ) = favoriteService.removeFavoriteLecture(lectureId)
}