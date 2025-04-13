package com.kiratnine.ktcourse.dto.auth

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class AuthRegisterInputDto(
    val name: String,
    val login: String,
    val password: String,
)