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
class Skill(
    val skillName: String,
    val proficiency: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DbFields.PROFILE_ID)
    var profile: Profile? = null,
) : BaseEntity()