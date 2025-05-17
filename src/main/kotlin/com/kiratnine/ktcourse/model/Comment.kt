package com.kiratnine.ktcourse.model

import com.kiratnine.ktcourse.model.constant.DbFields
import com.kiratnine.ktcourse.model.converter.MapToJsonConverter
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Entity
class Comment(
    @Convert(converter = MapToJsonConverter::class)
    val text: LocalizedString,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbFields.LECTURE_ID, nullable = false)
    val lecture: Lecture,

    @ManyToOne
    @JoinColumn(name = DbFields.PROFILE_ID, nullable = false)
    val author: Profile,

    val createdAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity()