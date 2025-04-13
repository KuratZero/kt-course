package com.kiratnine.ktcourse.security

import com.kiratnine.ktcourse.model.Profile
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * @author Artemii Kazakov (kiratnine@)
 */
class UserPrincipal(
    private val profile: Profile
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority("ROLE_${profile.role.name}"))

    override fun getPassword() = profile.password
    override fun getUsername() = profile.login

    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}