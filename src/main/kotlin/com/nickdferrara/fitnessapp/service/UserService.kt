package com.nickdferrara.fitnessapp.service

import com.nickdferrara.fitnessapp.models.UserEntity
import com.nickdferrara.fitnessapp.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository,
    val roleService: RoleService,
    val passwordEncoder: PasswordEncoder
) {
    fun findByUsername(username: String) = userRepository.findByUsername(username)

    fun existsByUsername(username: String) = userRepository.existsByUsername(username)

    fun save(user: UserEntity): UserEntity {
        if (existsByUsername(user.username)) {
            throw RuntimeException("Username already exists")
        }

        val registerUser = UserEntity(
            username = user.username,
            password = passwordEncoder.encode(user.password),
            roles = MutableList(1) { roleService.findByName("USER") }
        )

        return userRepository.save(registerUser)
    }
}