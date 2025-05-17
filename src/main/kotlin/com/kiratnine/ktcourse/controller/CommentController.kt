package com.kiratnine.ktcourse.controller

import com.kiratnine.ktcourse.dto.comment.CommentDto
import com.kiratnine.ktcourse.service.CommentService
import jakarta.validation.constraints.Max
import org.springframework.web.bind.annotation.DeleteMapping
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
class CommentController(
    private val commentService: CommentService,
) {
    @GetMapping("/comments/{lectureId}")
    fun getComments(
        @PathVariable("lectureId") lectureId: Long,
        @RequestParam(required = false) lang: String = "ru",
    ): List<CommentDto> =
        commentService.getCommentsByLectureId(lectureId, lang)

    @DeleteMapping("/comments/{id}")
    fun deleteComment(
        @PathVariable("id") id: Long
    ) = commentService.deleteComment(id)

    @PostMapping("/comments/{lectureId}")
    fun addComment(
        @PathVariable("lectureId") lectureId: Long,
        @RequestBody text: @Max(200) String
    ): Long = commentService.addComment(lectureId, text)
}