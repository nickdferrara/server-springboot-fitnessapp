package com.nickdferrara.fitnessapp.controller

import com.nickdferrara.fitnessapp.dto.LoginRequestDto
import com.nickdferrara.fitnessapp.dto.LoginResponseDto
import com.nickdferrara.fitnessapp.dto.RegisterRequestDto
import com.nickdferrara.fitnessapp.dto.RegisterResponseDto
import com.nickdferrara.fitnessapp.extension.toModel
import com.nickdferrara.fitnessapp.service.TokenService
import com.nickdferrara.fitnessapp.service.UserService
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
    fun login(@RequestBody loginRequestDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {
        val authentication = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequestDto.username,
                loginRequestDto.password
            )
        )
        val token = tokenService.generateToken(authentication)
        return ResponseEntity<LoginResponseDto>(LoginResponseDto(token), HttpStatus.OK)
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequestDto: RegisterRequestDto): ResponseEntity<RegisterResponseDto> {
        val savedUser = userService.save(registerRequestDto.toModel())
        return ResponseEntity<RegisterResponseDto>(RegisterResponseDto(savedUser.username), HttpStatus.CREATED)
    }
}