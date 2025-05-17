package com.kiratnine.ktcourse.controller

import com.kiratnine.ktcourse.dto.auth.AuthLoginInputDto
import com.kiratnine.ktcourse.dto.auth.AuthRegisterInputDto
import com.kiratnine.ktcourse.dto.auth.JwtUserResponseDto
import com.kiratnine.ktcourse.service.ProfileService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Artemii Kazakov (kiratnine@)
 */
@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val profileService: ProfileService,
) {
    @PostMapping("/register")
    @Operation(summary = "Основная ручка для регистрации/создания пользователя")
    fun register(@RequestBody request: AuthRegisterInputDto): JwtUserResponseDto =
        profileService.registerProfile(request)

    @PostMapping("/login")
    @Operation(summary = "Основная ручка для лог ина пользователя")
    fun login(@RequestBody request: AuthLoginInputDto): JwtUserResponseDto =
        profileService.loginProfile(request)
}