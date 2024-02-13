package com.nickdferrara.fitnessapp.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController {

    @GetMapping("/v1/user/login")
    fun login(principal: Principal ): String {
        return "Hello ${principal.name}"
    }
}