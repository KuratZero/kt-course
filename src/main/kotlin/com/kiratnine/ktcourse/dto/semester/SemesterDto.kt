package com.kiratnine.ktcourse.dto.semester

import com.kiratnine.ktcourse.dto.lecture.LectureDto

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class SemesterDto(
    val id: Long,
    val title: String,
    val description: String,
    val position: Long,
    val lectures: List<LectureDto>,
)