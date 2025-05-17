package com.kiratnine.ktcourse.mapper

import com.kiratnine.ktcourse.dto.profile.ContactDto
import com.kiratnine.ktcourse.dto.profile.ContactTypeDto
import com.kiratnine.ktcourse.dto.profile.EducationDto
import com.kiratnine.ktcourse.dto.profile.ProfileDto
import com.kiratnine.ktcourse.dto.profile.WorkExperienceDto
import com.kiratnine.ktcourse.model.Contact
import com.kiratnine.ktcourse.model.ContactType
import com.kiratnine.ktcourse.model.Education
import com.kiratnine.ktcourse.model.Profile
import com.kiratnine.ktcourse.model.WorkExperience

/**
 * @author Artemii Kazakov (kiratnine@)
 */
fun Profile.toDto(avatarUrl: String?, isEditable: Boolean): ProfileDto =
    ProfileDto(
        login = login,
        name = name,
        avatarUrl = avatarUrl,
        workExperience = workExperience?.toDto(),
        education = education?.toDto(),
        contacts = contacts.map { it.toDto() },
        isEditable = isEditable
    )

fun WorkExperience.toDto(): WorkExperienceDto =
    WorkExperienceDto(
        companyName = companyName,
        jobName = jobName,
    )

fun WorkExperienceDto.toModel(profile: Profile): WorkExperience =
    WorkExperience(
        companyName = companyName,
        jobName = jobName,
        profile = profile,
    )

fun Education.toDto(): EducationDto =
    EducationDto(
        name = name,
        degree = degree,
    )

fun EducationDto.toModel(profile: Profile): Education =
    Education(
        name = name,
        degree = degree,
        profile = profile,
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
        ContactType.TELEGRAM -> ContactTypeDto.TELEGRAM
    }

fun ContactTypeDto.toModel(): ContactType =
    when (this) {
        ContactTypeDto.PHONE -> ContactType.PHONE
        ContactTypeDto.EMAIL -> ContactType.EMAIL
        ContactTypeDto.TELEGRAM -> ContactType.TELEGRAM
    }