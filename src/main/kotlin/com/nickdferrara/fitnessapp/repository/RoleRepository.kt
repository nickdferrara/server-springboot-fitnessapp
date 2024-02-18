package com.nickdferrara.fitnessapp.repository


import com.nickdferrara.fitnessapp.models.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository: JpaRepository<Role, UUID>{
    fun findByName(name: String): Role?
}