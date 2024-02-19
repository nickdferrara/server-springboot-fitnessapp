package com.nickdferrara.fitnessapp.security

import com.nickdferrara.fitnessapp.models.Role
import com.nickdferrara.fitnessapp.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
    val userRepository: UserRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
        return User(user.username, user.password, mapRolesToAuthorities(user.roles))
    }

    fun mapRolesToAuthorities(roles: List<Role>): List<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.name) }
    }
}