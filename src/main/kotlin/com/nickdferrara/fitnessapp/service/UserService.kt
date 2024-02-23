package com.nickdferrara.fitnessapp.service

import com.nickdferrara.fitnessapp.models.Role
import com.nickdferrara.fitnessapp.models.UserEntity
import com.nickdferrara.fitnessapp.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository,
    val roleService: RoleService,
    val passwordEncoder: PasswordEncoder
): UserDetailsService {
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

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
        return User(user.username, user.password, mapRolesToAuthorities(user.roles))
    }

    fun mapRolesToAuthorities(roles: List<Role>): List<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.name) }
    }
}