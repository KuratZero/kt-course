package com.kiratnine.ktcourse.dto.lecture

import java.time.LocalDateTime

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class LectureDto(
    val imageUrl: String?,
    val presentationUrl: String?,
    val slug: String,
    val title: String,
    val description: String,
    val date: LocalDateTime,
    val topicName: String,
    val profiles: Set<LectureProfileDto>
)
