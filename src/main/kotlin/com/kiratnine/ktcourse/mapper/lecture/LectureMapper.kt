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
    imageUrl: String?,
    presentationUrl: String?
): LectureDto =
    LectureDto(
        slug = slug,
        title = title,
        description = description,
        date = date,
        topicName = topicName,
        profiles = profiles.map { it.toLectureDto(avatarUrlsById.getOrElse(it.id!!) { null }) }.toSet(),
        imageUrl = imageUrl,
        presentationUrl = presentationUrl
    )

fun Profile.toLectureDto(avatarUrl: String?): LectureProfileDto =
    LectureProfileDto(
        login = login,
        name = name,
        avatarUrl = avatarUrl
    )