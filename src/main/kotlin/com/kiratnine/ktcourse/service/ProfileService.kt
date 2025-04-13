package com.kiratnine.ktcourse.service

import com.kiratnine.ktcourse.dto.auth.AuthLoginInputDto
import com.kiratnine.ktcourse.dto.auth.AuthRegisterInputDto
import com.kiratnine.ktcourse.dto.auth.JwtUserResponseDto
import com.kiratnine.ktcourse.dto.profile.ContactDto
import com.kiratnine.ktcourse.dto.profile.EducationDto
import com.kiratnine.ktcourse.dto.profile.ProfileDto
import com.kiratnine.ktcourse.dto.profile.SkillDto
import com.kiratnine.ktcourse.dto.profile.WorkExperienceDto
import com.kiratnine.ktcourse.exception.BadRequestException
import com.kiratnine.ktcourse.mapper.toDto
import com.kiratnine.ktcourse.mapper.toModel
import com.kiratnine.ktcourse.model.Profile
import com.kiratnine.ktcourse.model.Role
import com.kiratnine.ktcourse.repository.ProfileRepository
import com.kiratnine.ktcourse.security.CurrentUser
import com.kiratnine.ktcourse.security.JwtUtil
import com.kiratnine.ktcourse.service.minio.ProfileMinioService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class ProfileService(
    private val profileRepository: ProfileRepository,
    private val avatarService: ProfileMinioService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
) {
    fun registerProfile(input: AuthRegisterInputDto): JwtUserResponseDto {
        if (profileRepository.findByLogin(input.login) != null) {
            throw BadRequestException("User with login ${input.login} already exists")
        }

        val profile = Profile(
            name = input.name,
            login = input.login,
            password = passwordEncoder.encode(input.password),
            role = if (input.login == "kiratnine") Role.ADMIN else Role.USER
        )

        profileRepository.save(profile)

        return JwtUserResponseDto(
            jwtUtil.generateToken(profile.login, profile.role.name),
            profile.login
        )
    }

    fun loginProfile(input: AuthLoginInputDto): JwtUserResponseDto {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(input.login, input.password)
        )

        val profile = getSafeProfile(input.login)
        return JwtUserResponseDto(
            jwtUtil.generateToken(profile.login, profile.role.name),
            profile.login
        )
    }

    fun getProfiles(): List<ProfileDto> =
        profileRepository.findAll().map {
            it.toDto(
                avatarUrl = avatarService.getAvatarUrlOrNull(it),
                isEditable = isEditableProfile(it.login)
            )
        }

    fun getProfile(login: String): ProfileDto {
        val profile = getSafeProfile(login)
        return profile.toDto(
            avatarUrl = avatarService.getAvatarUrlOrNull(profile),
            isEditable = isEditableProfile(profile.login),
        )
    }

    fun deleteProfile(login: String) {
        validateProfileActions(login)
        val profile = getSafeProfile(login)
        profileRepository.delete(profile)
    }

    fun replaceAvatar(login: String, file: MultipartFile) {
        validateProfileActions(login)
        val profile = getSafeProfile(login)
        profile.avatarId = avatarService.uploadAvatar(profile, file)
        profileRepository.save(profile)
    }

    fun replaceSkills(login: String, skills: List<SkillDto>) {
        validateProfileActions(login)
        val profile = getSafeProfile(login)
        profile.replaceSkills(skills.map { it.toModel() })
        profileRepository.save(profile)
    }

    fun replaceWorkExperiences(login: String, workExperiences: List<WorkExperienceDto>) {
        validateProfileActions(login)
        val profile = getSafeProfile(login)
        profile.replaceWorkExperiences(workExperiences.map { it.toModel() })
        profileRepository.save(profile)
    }

    fun replaceEducations(login: String, educations: List<EducationDto>) {
        validateProfileActions(login)
        val profile = getSafeProfile(login)
        profile.replaceEducations(educations.map { it.toModel() })
        profileRepository.save(profile)
    }

    fun replaceContacts(login: String, contacts: List<ContactDto>) {
        validateProfileActions(login)
        val profile = getSafeProfile(login)
        profile.replaceContacts(contacts.map { it.toModel() })
        profileRepository.save(profile)
    }

    private fun isEditableProfile(login: String): Boolean =
        CurrentUser.isAuthenticated() &&
            (CurrentUser.login() == login || getSafeProfile(CurrentUser.login()).role == Role.ADMIN)

    private fun validateProfileActions(login: String) {
        if (getSafeProfile(CurrentUser.login()).role == Role.ADMIN
            || CurrentUser.login() == login
        ) {
            return
        } else {
            throw BadRequestException("Do not allow profile actions")
        }
    }

    private fun getSafeProfile(login: String): Profile =
        profileRepository.findByLogin(login)
            ?: throw NoSuchElementException("No profile found with login: $login")
}