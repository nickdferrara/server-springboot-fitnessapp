package com.nickdferrara.fitnessapp.service

import com.nickdferrara.fitnessapp.repository.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    val roleRepository: RoleRepository
) {
    fun findByName(name: String) = roleRepository.findByName(name)
}