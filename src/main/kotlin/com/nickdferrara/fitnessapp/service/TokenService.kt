package com.nickdferrara.fitnessapp.service


import jakarta.servlet.http.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.stream.Collectors

@Service
class TokenService(
    val jwtEncoder: JwtEncoder,
    val jwtDecoder: JwtDecoder
) {

    fun generateAccessToken(authentication: Authentication): String {
        val scope = buildAuthorities(authentication)
        val claims = buildClaims(authentication, 86400, scope)
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    fun generateRefreshToken(authentication: Authentication): String {
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

    fun isRefreshTokenExpired(request: HttpServletRequest, response: HttpServletResponse): Boolean {
        val refreshToken = request.getHeader("Authorization")?.removePrefix("Bearer ")
            ?: throw RuntimeException("No refresh token found")

        val token = jwtDecoder.decode(refreshToken)
        return token.expiresAt?.isBefore(Instant.now()) == true
    }

}