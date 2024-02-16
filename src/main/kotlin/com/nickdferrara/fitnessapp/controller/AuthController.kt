package com.nickdferrara.fitnessapp.controller

import com.nickdferrara.fitnessapp.service.TokenService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/token")
class AuthController(
    val tokenService: TokenService
) {
    @PostMapping
    fun token(authentication: Authentication): String =
        tokenService.generateToken(authentication)

}