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
import com.kiratnine.ktcourse.service.s3.ProfileS3Service
import com.kiratnine.ktcourse.translator.service.TranslatorService
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
    private val avatarService: ProfileS3Service,
    private val translatorService: TranslatorService,
) {
    fun addComment(lectureId: Long, text: String): Long {
        val comment = Comment(
            text = translatorService.translateString(text),
            lecture = getLecture(lectureId),
            author = getProfile(CurrentUser.login()),
        )

        return commentRepository.save(comment).id!!
    }

    fun deleteComment(commentId: Long) {
        val comment = commentRepository.findById(commentId).orElseThrow()
        val profile = profileRepository.findByLogin(CurrentUser.login())?.role
        if (CurrentUser.login() != comment.author.login && profile != Role.ADMIN) {
            throw BadRequestException("Can't delete comment")
        }
        commentRepository.deleteById(commentId)
    }

    fun getCommentsByLectureId(lectureId: Long, lang: String): List<CommentDto> =
        commentRepository.findByLectureOrderByCreatedAtAsc(getLecture(lectureId))
            .map { it.toDto(avatarService.getAvatarUrlOrNull(it.author), lang) }

    private fun getProfile(login: String): Profile =
        profileRepository.findByLogin(login)
            ?: throw EntityNotFoundException("Profile Not Found")

    private fun getLecture(lectureId: Long): Lecture =
        lectureRepository.findById(lectureId)
            .orElseThrow { EntityNotFoundException("Lecture Not Found") }
}