package com.kiratnine.ktcourse.mapper.semester

import com.kiratnine.ktcourse.dto.lecture.LectureDto
import com.kiratnine.ktcourse.dto.semester.SemesterDto
import com.kiratnine.ktcourse.model.Semester

/**
 * @author Artemii Kazakov (kiratnine@)
 */
fun Semester.toDto(lectures: List<LectureDto>): SemesterDto =
    SemesterDto(
        id = id!!,
        title = title,
        description = description,
        position = position,
        lectures = lectures,
    )