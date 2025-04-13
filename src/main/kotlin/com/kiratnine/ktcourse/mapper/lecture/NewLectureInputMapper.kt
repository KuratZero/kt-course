package com.kiratnine.ktcourse.mapper.lecture

import com.kiratnine.ktcourse.dto.lecture.NewLectureInputDto
import com.kiratnine.ktcourse.model.Lecture

/**
 * @author Artemii Kazakov (kiratnine@)
 */
fun NewLectureInputDto.toModel(): Lecture =
    Lecture(
        slug = slug,
        title = title,
        description = description,
        date = date,
        topicName = topicName,
    )