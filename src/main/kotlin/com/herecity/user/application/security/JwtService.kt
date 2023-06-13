package com.herecity.user.application.security

import com.herecity.common.config.app.AppProperties
import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.output.UserQueryOutputPort
import com.herecity.user.domain.vo.UserDetail
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date
import java.util.UUID

@Component
class JwtService(
    private val appProperties: AppProperties,
    private val userQueryOutputPort: UserQueryOutputPort,
) {
    private fun getBodyFromToken(token: String, key: String): Claims =
        Jwts.parserBuilder().setSigningKey(key.toByteArray()).build().parseClaimsJws(token).body

    private fun getSigningKey(): Key = Keys.hmacShaKeyFor(appProperties.auth.accessTokenSecret.toByteArray())

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
            .setExpiration(Date(now.time + appProperties.auth.accessTokenTtlMsec))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun getAuthentication(token: String, key: String = appProperties.auth.accessTokenSecret): Authentication {
        getBodyFromToken(token, key).let {
            val user = userQueryOutputPort.getById(UUID.fromString(it["id"].toString()))
            val userDetails = UserDetail(user)
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
            log.error(it.message, it)
            throw JwtException(it.message)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private const val BEARER_PREFIX = "Bearer "
        const val AUTHORIZATION_HEADER_KEY = "Authorization"
    }
}
