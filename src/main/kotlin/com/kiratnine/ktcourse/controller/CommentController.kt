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
import org.springframework.web.bind.annotation.RestController

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@RestController
@RequestMapping("/v1")
class CommentController(
    private val commentService: CommentService,
) {
    @GetMapping("/comments/{lectureSlug}")
    fun getComments(
        @PathVariable("lectureSlug") lectureSlug: String
    ): List<CommentDto> =
        commentService.getCommentsByLectureId(lectureSlug)

    @DeleteMapping("/comments/{id}")
    fun deleteComment(
        @PathVariable("id") id: Long
    ) = commentService.deleteComment(id)

    @PostMapping("/comments/{lectureSlug}")
    fun addComment(
        @PathVariable("lectureSlug") lectureSlug: String,
        @RequestBody text: @Max(200) String
    ) = commentService.addComment(lectureSlug, text)
}