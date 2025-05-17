package com.kiratnine.ktcourse.model

import jakarta.persistence.Entity
import jakarta.persistence.OneToOne

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Entity
class Education(
    val name: String,
    val degree: String,
    val startDate: String,
    val endDate: String,

    @OneToOne(mappedBy = "education")
    var profile: Profile,
) : BaseEntity()