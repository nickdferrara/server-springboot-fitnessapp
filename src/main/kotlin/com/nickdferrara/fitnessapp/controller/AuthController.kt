package com.nickdferrara.fitnessapp.controller

import com.nickdferrara.fitnessapp.dto.LoginRequestDto
import com.nickdferrara.fitnessapp.dto.AuthResponseDto
import com.nickdferrara.fitnessapp.dto.RegisterRequestDto
import com.nickdferrara.fitnessapp.dto.RegisterResponseDto
import com.nickdferrara.fitnessapp.extension.toModel
import com.nickdferrara.fitnessapp.service.TokenService
import com.nickdferrara.fitnessapp.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    val authManager: AuthenticationManager,
    val userService: UserService

) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto): ResponseEntity<AuthResponseDto> {
        val authentication = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequestDto.email,
                loginRequestDto.password
            )
        )
        val accessToken = tokenService.generateAccessToken(authentication)
        val refreshToken = tokenService.generateRefreshToken(authentication)
        return ResponseEntity<AuthResponseDto>(AuthResponseDto(accessToken, refreshToken), HttpStatus.OK)
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequestDto: RegisterRequestDto): ResponseEntity<RegisterResponseDto> {

        if (userService.existsByEmail(registerRequestDto.email)) {
            throw RuntimeException("Username already exists")
        }

        val savedUser = userService.save(registerRequestDto.toModel())
        return ResponseEntity<RegisterResponseDto>(RegisterResponseDto(savedUser.email), HttpStatus.CREATED)
    }

    @PostMapping("/refreshtoken")
    fun refreshToken(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<AuthResponseDto> {

        if (tokenService.isRefreshTokenExpired(request, response)) {
            throw RuntimeException("Refresh token expired")
        }

        if (userService.existsByEmail(request.userPrincipal.name)) {
            throw RuntimeException("User not found")
        }

        val user  = userService.findByEmail(request.userPrincipal.name)
        val authentication = UsernamePasswordAuthenticationToken(user?.email, user?.password)
        val accessToken = tokenService.generateAccessToken(authentication)
        val refreshToken = tokenService.generateRefreshToken(authentication)
        return ResponseEntity<AuthResponseDto>(AuthResponseDto(accessToken, refreshToken), HttpStatus.OK)
    }
}