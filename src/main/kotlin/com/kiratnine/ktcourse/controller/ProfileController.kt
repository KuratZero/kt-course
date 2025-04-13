package com.kiratnine.ktcourse.controller

import com.kiratnine.ktcourse.dto.profile.ContactDto
import com.kiratnine.ktcourse.dto.profile.EducationDto
import com.kiratnine.ktcourse.dto.profile.ProfileDto
import com.kiratnine.ktcourse.dto.profile.SkillDto
import com.kiratnine.ktcourse.dto.profile.WorkExperienceDto
import com.kiratnine.ktcourse.service.ProfileService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@RestController
@RequestMapping("/api/v1")
class ProfileController(
    private val profileService: ProfileService
) {
    @GetMapping("/profiles")
    fun getProfiles(): List<ProfileDto> = profileService.getProfiles()

    @GetMapping("/profiles/{login}")
    @Operation(summary = "Ручка для странички профиля")
    fun getProfileById(@PathVariable login: String): ProfileDto = profileService.getProfile(login)

    @DeleteMapping("/profiles/{login}")
    fun deleteProfile(@PathVariable login: String) = profileService.deleteProfile(login)

    @PatchMapping("/profiles/{login}/avatar", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "Ручка для смены аватара")
    fun replaceAvatar(
        @PathVariable login: String,
        @RequestPart file: MultipartFile
    ) = profileService.replaceAvatar(login, file)

    @PatchMapping("/profiles/{login}/skills")
    @Operation(summary = "Ручка для смены скиллов, сколь угодно")
    fun replaceSkills(
        @PathVariable login: String,
        @RequestBody skills: List<SkillDto>
    ) = profileService.replaceSkills(login, skills)

    @PatchMapping("/profiles/{login}/workExperiences")
    @Operation(summary = "Ручка для смены опыта работы, с фронта разрешаем ровно одно, хотя можно сохранить сколько хочешь")
    fun replaceWorkExperiences(
        @PathVariable login: String,
        @RequestBody workExperiences: List<WorkExperienceDto>
    ) = profileService.replaceWorkExperiences(login, workExperiences)

    @PatchMapping("/profiles/{login}/educations")
    @Operation(summary = "Ручка для смены образования, с фронта разрешаем ровно одно, хотя можно сохранить сколько хочешь")
    fun replaceEducations(
        @PathVariable login: String,
        @RequestBody educations: List<EducationDto>
    ) = profileService.replaceEducations(login, educations)

    @PatchMapping("/profiles/{login}/contacts")
    @Operation(summary = "Ручка для смены контактов, с фронта разрешаем не больше трёх, хотя можно сохранить сколько хочешь")
    fun replaceContacts(
        @PathVariable login: String,
        @RequestBody contacts: List<ContactDto>
    ) = profileService.replaceContacts(login, contacts)
}