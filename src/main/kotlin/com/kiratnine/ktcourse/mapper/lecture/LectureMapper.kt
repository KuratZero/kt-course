package com.kiratnine.ktcourse.mapper.lecture

import com.kiratnine.ktcourse.dto.lecture.LectureDto
import com.kiratnine.ktcourse.dto.lecture.LectureProfileDto
import com.kiratnine.ktcourse.model.Lecture
import com.kiratnine.ktcourse.model.Profile

/**
 * @author Artemii Kazakov (kiratnine@)
 */
fun Lecture.toDto(
    avatarUrlsById: Map<Long, String?>,
    lang: String,
): LectureDto =
    LectureDto(
        id = id!!,
        title = title[lang]!!,
        description = description[lang]!!,
        date = date,
        profiles = profiles.map { it.toLectureDto(avatarUrlsById.getOrElse(it.id!!) { null }) }.toSet(),
        presentationId = presentationId
    )

fun Profile.toLectureDto(avatarUrl: String?): LectureProfileDto =
    LectureProfileDto(
        login = login,
        name = name,
        avatarUrl = avatarUrl
    )