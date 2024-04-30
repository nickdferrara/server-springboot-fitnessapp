package com.nickdferrara.fitnessapp

import com.nickdferrara.fitnessapp.models.Role
import com.nickdferrara.fitnessapp.repository.RoleRepository
import com.nickdferrara.fitnessapp.security.RsaKeyProperties
import com.nickdferrara.fitnessapp.service.RoleService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.*

@EnableConfigurationProperties(RsaKeyProperties::class)
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}