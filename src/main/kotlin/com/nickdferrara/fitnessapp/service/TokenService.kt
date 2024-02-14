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
        val now = Instant.now()

        val scope = authentication.authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .subject(authentication.name)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(3600))
            .subject(authentication.name)
            .claim("scope", scope)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}