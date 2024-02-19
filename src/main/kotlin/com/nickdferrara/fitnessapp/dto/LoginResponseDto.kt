package com.nickdferrara.fitnessapp.dto

class LoginResponseDto(
    val accessToken: String,
    val tokenType: String= "Bearer"
)