package com.kiratnine.ktcourse.dto.profile

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class ProfileDto(
    val login: String,
    val avatarUrl: String?,
    val name: String,
    val workExperience: WorkExperienceDto?,
    val education: EducationDto?,
    val contacts: List<ContactDto>,
    val isEditable: Boolean,
)