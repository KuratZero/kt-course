package com.kiratnine.ktcourse.mapper.semester

import com.kiratnine.ktcourse.dto.semester.NewSemesterInputDto
import com.kiratnine.ktcourse.model.Semester
import com.kiratnine.ktcourse.translator.service.TranslatorService

/**
 * @author Artemii Kazakov (kiratnine@)
 */
fun NewSemesterInputDto.toModel(translatorService: TranslatorService): Semester =
    Semester(
        title = translatorService.translateString(title),
        description = translatorService.translateString(description),
        position = position,
    )