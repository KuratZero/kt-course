package com.kiratnine.ktcourse.mapper.lecture

import com.kiratnine.ktcourse.dto.lecture.NewLectureInputDto
import com.kiratnine.ktcourse.model.Lecture
import com.kiratnine.ktcourse.translator.service.TranslatorService

/**
 * @author Artemii Kazakov (kiratnine@)
 */
fun NewLectureInputDto.toModel(translatorService: TranslatorService): Lecture =
    Lecture(
        title = translatorService.translateString(title),
        description = translatorService.translateString(description),
        date = date,
    )