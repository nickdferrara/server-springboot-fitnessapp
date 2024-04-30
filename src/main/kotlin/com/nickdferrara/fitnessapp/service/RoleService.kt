package com.nickdferrara.fitnessapp.service

import com.nickdferrara.fitnessapp.models.Role
import com.nickdferrara.fitnessapp.repository.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    val roleRepository: RoleRepository
) {
    fun findByName(name: String) = roleRepository.findByName(name)
    fun save(role: Role) = roleRepository.save(role)
}