package com.kiratnine.ktcourse.model

import jakarta.persistence.Entity
import jakarta.persistence.OneToOne

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Entity
class WorkExperience(
    val companyName: String,
    val jobName: String,

    @OneToOne(mappedBy = "workExperience")
    var profile: Profile,
) : BaseEntity()