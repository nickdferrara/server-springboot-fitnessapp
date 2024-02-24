package com.nickdferrara.fitnessapp.repository

import com.nickdferrara.fitnessapp.models.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserEntity, UUID> {
    fun findByEmail(username: String): UserEntity?
    fun existsByEmail(username: String): Boolean
}