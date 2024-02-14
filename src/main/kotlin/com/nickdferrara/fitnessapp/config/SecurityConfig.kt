package com.nickdferrara.fitnessapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
    val rsaKeys: RsaKeyProperties
) {

    @Bean
    fun userDetailsService(): InMemoryUserDetailsManager =
        InMemoryUserDetailsManager(User.withUsername("user")
            .password("{noop}password")
            .authorities("read")
            .build())

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { csrf -> csrf.disable() }
        http.authorizeHttpRequests { auth ->
            auth.anyRequest().authenticated()
        }
        http.oauth2ResourceServer { oauth2 ->
            oauth2.jwt(Customizer.withDefaults())
        }
        http.sessionManagement { session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        http.httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey).build()
    }
}