package com.kiratnine.ktcourse.dto.lecture

import java.time.LocalDateTime

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class LectureDto(
    val id: Long,
    val presentationId: String?,
    val title: String,
    val description: String,
    val date: LocalDateTime,
    val profiles: Set<LectureProfileDto>,
    val tags: Set<String>,
)
