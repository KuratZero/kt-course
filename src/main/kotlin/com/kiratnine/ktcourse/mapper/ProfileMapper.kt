package com.kiratnine.ktcourse.mapper

import com.kiratnine.ktcourse.dto.profile.ContactDto
import com.kiratnine.ktcourse.dto.profile.ContactTypeDto
import com.kiratnine.ktcourse.dto.profile.EducationDto
import com.kiratnine.ktcourse.dto.profile.ProfileDto
import com.kiratnine.ktcourse.dto.profile.SkillDto
import com.kiratnine.ktcourse.dto.profile.WorkExperienceDto
import com.kiratnine.ktcourse.model.Contact
import com.kiratnine.ktcourse.model.ContactType
import com.kiratnine.ktcourse.model.Education
import com.kiratnine.ktcourse.model.Profile
import com.kiratnine.ktcourse.model.Skill
import com.kiratnine.ktcourse.model.WorkExperience

/**
 * @author Artemii Kazakov (kiratnine@)
 */
fun Profile.toDto(avatarUrl: String?, isEditable: Boolean): ProfileDto =
    ProfileDto(
        login = login,
        name = name,
        avatarUrl = avatarUrl,
        skills = skills.map { it.toDto() },
        workExperience = workExperience.map { it.toDto() },
        education = education.map { it.toDto() },
        contacts = contacts.map { it.toDto() },
        isEditable = isEditable
    )

fun Skill.toDto(): SkillDto =
    SkillDto(
        skillName = skillName,
        proficiency = proficiency
    )

fun SkillDto.toModel(): Skill =
    Skill(
        skillName = skillName,
        proficiency = proficiency,
    )

fun WorkExperience.toDto(): WorkExperienceDto =
    WorkExperienceDto(
        companyName = companyName,
        jobName = jobName,
        location = location,
        description = description,
        startDate = startDate,
        endDate = endDate,
        occupationType = occupationType
    )

fun WorkExperienceDto.toModel(): WorkExperience =
    WorkExperience(
        companyName = companyName,
        jobName = jobName,
        location = location,
        description = description,
        startDate = startDate,
        endDate = endDate,
        occupationType = occupationType,
    )

fun Education.toDto(): EducationDto =
    EducationDto(
        name = name,
        degree = degree,
        startDate = startDate,
        endDate = endDate
    )

fun EducationDto.toModel(): Education =
    Education(
        name = name,
        degree = degree,
        startDate = startDate,
        endDate = endDate,
    )

fun Contact.toDto(): ContactDto =
    ContactDto(
        type = type.toDto(),
        contact = contact
    )

fun ContactDto.toModel(): Contact =
    Contact(
        type = type.toModel(),
        contact = contact,
    )

fun ContactType.toDto(): ContactTypeDto =
    when (this) {
        ContactType.PHONE -> ContactTypeDto.PHONE
        ContactType.EMAIL -> ContactTypeDto.EMAIL
    }

fun ContactTypeDto.toModel(): ContactType =
    when (this) {
        ContactTypeDto.PHONE -> ContactType.PHONE
        ContactTypeDto.EMAIL -> ContactType.EMAIL
    }