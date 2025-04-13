package com.kiratnine.ktcourse.dto.comment

import java.time.LocalDateTime

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class CommentDto(
    val text: String,
    val createdAt: LocalDateTime,
    val author: CommentProfileDto,
)
