package com.kiratnine.ktcourse.dto.semester

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class NewSemesterInputDto(
    val title: String,
    val description: String,
    val position: Long,
)