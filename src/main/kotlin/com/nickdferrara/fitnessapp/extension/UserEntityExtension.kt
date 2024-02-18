package com.nickdferrara.fitnessapp.extension

import com.nickdferrara.fitnessapp.dto.RegisterRequestDto
import com.nickdferrara.fitnessapp.models.UserEntity

fun RegisterRequestDto.toModel() =
    UserEntity(
        username = this.username,
        password = this.password
    )

fun UserEntity.toDto() =
    RegisterRequestDto(
        username = this.username,
        password = this.password
    )