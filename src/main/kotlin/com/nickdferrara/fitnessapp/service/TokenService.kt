package com.nickdferrara.fitnessapp.service


import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.stream.Collectors

@Service
class TokenService(
    val jwtEncoder: JwtEncoder
) {

    fun generateToken(authentication: Authentication): String {
        val scope = buildAuthorities(authentication)
        val claims = buildClaims(authentication, 86400, scope)
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    fun refreshToken(authentication: Authentication): String {
        val scope = buildAuthorities(authentication)
        val claims = buildClaims(authentication, 604800, scope)
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    private fun buildAuthorities(authentication: Authentication): String? =
        authentication.authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "))

    private fun buildClaims(
        authentication: Authentication,
        expiration: Long,
        scope: String?
    ): JwtClaimsSet? {
        val now = Instant.now()

        return JwtClaimsSet.builder()
            .issuer("self")
            .subject(authentication.name)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiration))
            .subject(authentication.name)
            .claim("scope", scope)
            .build()
    }
}