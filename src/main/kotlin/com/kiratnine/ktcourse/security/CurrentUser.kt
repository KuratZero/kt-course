package com.kiratnine.ktcourse.security

import org.springframework.security.core.context.SecurityContextHolder

/**
 * @author Artemii Kazakov (kiratnine@)
 */
object CurrentUser {
    fun isAuthenticated(): Boolean =
        SecurityContextHolder.getContext().authentication.name != "anonymousUser"

    fun login(): String {
        val auth = SecurityContextHolder.getContext().authentication
        if (!isAuthenticated()) throw IllegalStateException("No authenticated user")
        return auth?.name ?: throw IllegalStateException("No authenticated user")
    }
}