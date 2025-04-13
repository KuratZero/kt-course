package com.kiratnine.ktcourse.service

import com.kiratnine.ktcourse.repository.ProfileRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@Service
class UserDetailsServiceImpl(
    private val profileRepository: ProfileRepository,
) : UserDetailsService {
    override fun loadUserByUsername(login: String): UserDetails {
        val profile = profileRepository.findByLogin(login)
            ?: throw UsernameNotFoundException("User not found with login: $login")

        return User(
            login,
            profile.password,
            listOf(SimpleGrantedAuthority("ROLE_${profile.role.name}"))
        )
    }
}