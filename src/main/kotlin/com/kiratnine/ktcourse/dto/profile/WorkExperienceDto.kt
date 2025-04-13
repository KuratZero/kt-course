package com.kiratnine.ktcourse.dto.profile

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class WorkExperienceDto(
    val companyName: String,
    val jobName: String,
    val location: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val occupationType: String,
)
