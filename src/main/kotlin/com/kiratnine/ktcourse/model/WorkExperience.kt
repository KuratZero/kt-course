package com.kiratnine.ktcourse.model

import com.kiratnine.ktcourse.model.constant.DbFields
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Entity
class WorkExperience(
    val companyName: String,
    val jobName: String,
    val location: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val occupationType: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbFields.PROFILE_ID)
    var profile: Profile? = null,
) : BaseEntity()