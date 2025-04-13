package com.kiratnine.ktcourse.service

import com.kiratnine.ktcourse.dto.comment.CommentDto
import com.kiratnine.ktcourse.exception.BadRequestException
import com.kiratnine.ktcourse.mapper.comment.toDto
import com.kiratnine.ktcourse.model.Comment
import com.kiratnine.ktcourse.model.Lecture
import com.kiratnine.ktcourse.model.Profile
import com.kiratnine.ktcourse.model.Role
import com.kiratnine.ktcourse.repository.CommentRepository
import com.kiratnine.ktcourse.repository.LectureRepository
import com.kiratnine.ktcourse.repository.ProfileRepository
import com.kiratnine.ktcourse.security.CurrentUser
import com.kiratnine.ktcourse.service.minio.ProfileMinioService
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val profileRepository: ProfileRepository,
    private val lectureRepository: LectureRepository,
    private val avatarService: ProfileMinioService,
) {
    fun addComment(lectureSlug: String, text: String) {
        val comment = Comment(
            text = text,
            lecture = getLecture(lectureSlug),
            author = getProfile(CurrentUser.login()),
        )

        commentRepository.save(comment)
    }

    fun deleteComment(commentId: Long) {
        val comment = commentRepository.findById(commentId).orElseThrow()
        val profile = profileRepository.findByLogin(CurrentUser.login())?.role
        if (CurrentUser.login() != comment.author.login && profile != Role.ADMIN) {
            throw BadRequestException("Can't delete comment")
        }
        commentRepository.deleteById(commentId)
    }

    fun getCommentsByLectureId(lectureSlug: String): List<CommentDto> =
        commentRepository.findByLectureOrderByCreatedAtAsc(getLecture(lectureSlug))
            .map { it.toDto(avatarService.getAvatarUrlOrNull(it.author)) }

    private fun getProfile(login: String): Profile =
        profileRepository.findByLogin(login)
            ?: throw EntityNotFoundException("Profile Not Found")

    private fun getLecture(lectureSlug: String): Lecture =
        lectureRepository.findBySlug(lectureSlug)
            .orElseThrow { EntityNotFoundException("Lecture Not Found") }
}