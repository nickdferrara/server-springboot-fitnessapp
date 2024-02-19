package com.nickdferrara.fitnessapp.config

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
    val rsaKeys: RsaKeyProperties
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { csrf -> csrf.disable() }
        http.authorizeHttpRequests { auth -> auth
            .requestMatchers("/api/v1/register").permitAll()
            .requestMatchers("/api/v1/login").permitAll()
            .anyRequest().authenticated()
        }
        http.oauth2ResourceServer { oauth2 -> oauth2
            .jwt(Customizer.withDefaults())
        }
        http.sessionManagement { session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        http.exceptionHandling{ ex -> ex
            .authenticationEntryPoint(BearerTokenAuthenticationEntryPoint())
            .accessDeniedHandler(BearerTokenAccessDeniedHandler())
        }
        return http.build()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder =
        NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey).build()

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(rsaKeys.publicKey).privateKey(rsaKeys.privateKey).build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager =
        authenticationConfiguration.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}