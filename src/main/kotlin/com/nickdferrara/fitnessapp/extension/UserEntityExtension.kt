package com.nickdferrara.fitnessapp.extension

import com.nickdferrara.fitnessapp.dto.RegisterRequestDto
import com.nickdferrara.fitnessapp.models.UserEntity

fun RegisterRequestDto.toModel() =
    UserEntity(
        email = this.email      ,
        password = this.password
    )

fun UserEntity.toDto() =
    RegisterRequestDto(
        email = this.email,
        password = this.password
    )