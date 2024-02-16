package com.nickdferrara.fitnessapp.controller

import com.nickdferrara.fitnessapp.dto.LoginRequestDto
import com.nickdferrara.fitnessapp.service.TokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class AuthController(
    val tokenService: TokenService,
    val authManager: AuthenticationManager
) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto): String {
        val authentication = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequestDto.username,
                loginRequestDto.password
            )
        )
        return tokenService.generateToken(authentication)
    }
}