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

    fun findByEmail(username: String) = userRepository.findByEmail(username)

    fun existsByEmail(username: String) = userRepository.existsByEmail(username)

    fun save(user: UserEntity): UserEntity {

        val registerUser = UserEntity(
            email = user.email,
            password = passwordEncoder.encode(user.password),
            roles = MutableList(1) { roleService.findByName("USER") }
        )

        return userRepository.save(registerUser)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw UsernameNotFoundException("User not found")
        return User(user.email, user.password, mapRolesToAuthorities(user.roles))
    }

    fun mapRolesToAuthorities(roles: List<Role>): List<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.name) }
    }
}