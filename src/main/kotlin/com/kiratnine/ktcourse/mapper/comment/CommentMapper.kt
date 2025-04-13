package com.kiratnine.ktcourse.mapper.comment

import com.kiratnine.ktcourse.dto.comment.CommentDto
import com.kiratnine.ktcourse.dto.comment.CommentProfileDto
import com.kiratnine.ktcourse.model.Comment
import com.kiratnine.ktcourse.model.Profile

/**
 * @author Artemii Kazakov (kiratnine@)
 */
fun Comment.toDto(avatarUrl: String?): CommentDto =
    CommentDto(
        text = text,
        createdAt = createdAt,
        author = author.toDto(avatarUrl),
    )

fun Profile.toDto(avatarUrl: String?): CommentProfileDto =
    CommentProfileDto(
        login = login,
        name = name,
        avatarUrl = avatarUrl,
    )