package com.kiratnine.ktcourse.dto.lecture

import java.time.LocalDateTime

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class NewLectureInputDto(
    val title: String,
    val description: String,
    val date: LocalDateTime,
    val tags: Set<String>,
)