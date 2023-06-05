package com.herecity.user.application.security

import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.output.UserQueryOutputPort
import com.herecity.user.domain.UserDetail
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtService(
    @Value("\${jwt.token.secret}") private val secretKey: String,
    private val userQueryOutputPort: UserQueryOutputPort,
) {
    private fun getBodyFromToken(token: String, key: String): Claims =
        Jwts.parserBuilder().setSigningKey(key.toByteArray()).build().parseClaimsJws(token).body

    private fun getSigningKey(): Key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun createAccessToken(userDto: UserDto): String {
        val claims: Claims = Jwts.claims().setSubject(userDto.id.toString())
        claims["id"] = userDto.id.toString()
        claims["email"] = userDto.email
        claims["displayName"] = userDto.displayName
        claims["role"] = userDto.role.name

        val now = Date()
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDto.id.toString())
            .setIssuedAt(now)
            .setExpiration(Date(now.time + ACCESS_TOKEN_VALID_TIME))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun getAuthentication(token: String, passwordEncoder: PasswordEncoder, key: String = secretKey): Authentication {
        getBodyFromToken(token, key).let {
            val user = userQueryOutputPort.getById(UUID.fromString(it["id"].toString()))
            val userDetails = UserDetail(user, passwordEncoder)
            return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
        }
    }

    fun resolveToken(token: String): String {
        return if (token.indexOf(BEARER_PREFIX) > -1) token.replace(BEARER_PREFIX, "") else ""
    }

    fun validateToken(token: String) {
        runCatching {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
        }.onFailure {
            throw JwtException(it.message)
        }
    }

    companion object {
        private const val ACCESS_TOKEN_VALID_TIME = 86400000L // 1 day
        private const val BEARER_PREFIX = "Bearer "
        const val AUTHORIZATION_HEADER_KEY = "Authorization"
    }
}
