package com.kiratnine.ktcourse.model

import com.kiratnine.ktcourse.model.constant.DbFields
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
    val text: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbFields.LECTURE_ID, nullable = false)
    val lecture: Lecture,

    @ManyToOne
    @JoinColumn(name = DbFields.PROFILE_ID, nullable = false)
    val author: Profile,

    val createdAt: LocalDateTime = LocalDateTime.now(),
) : BaseEntity()