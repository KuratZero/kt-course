package com.kiratnine.ktcourse.dto.profile

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class ProfileDto(
    val login: String,
    val avatarUrl: String?,
    val name: String,
    val skills: List<SkillDto>,
    val workExperience: List<WorkExperienceDto>,
    val education: List<EducationDto>,
    val contacts: List<ContactDto>,
    val isEditable: Boolean,
)