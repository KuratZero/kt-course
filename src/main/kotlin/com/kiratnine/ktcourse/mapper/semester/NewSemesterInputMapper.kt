package com.kiratnine.ktcourse.mapper.semester

import com.kiratnine.ktcourse.dto.semester.NewSemesterInputDto
import com.kiratnine.ktcourse.model.Semester

/**
 * @author Artemii Kazakov (kiratnine@)
 */
fun NewSemesterInputDto.toModel(): Semester =
    Semester(
        title = title,
        description = description,
        position = position,
    )