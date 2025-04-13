package com.kiratnine.ktcourse.dto.auth

/**
 * @author Artemii Kazakov (kiratnine@)
 */
data class JwtUserResponseDto(
    val jwtToken: String,
    val login: String,
)