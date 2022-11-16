package com.herecity.user.application.security

import com.herecity.user.application.port.output.UserQueryOutputPort
import com.herecity.user.domain.UserSecurity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtService(private val userQueryOutputPort: UserQueryOutputPort) {
  companion object {
    private val secretKey: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun getIdFromToken(token: String): String {
      return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    }
  }


  private val accessTokenValidTime = 30 * 60 * 1000L

  private val refreshTokenValidTime = 30 * 24 * 60 * 60 * 1000L

  private fun createToken(userPk: String, validTime: Long): String {
    val claims: Claims = Jwts.claims().setSubject(userPk)
    claims["userPk"] = userPk
    val now = Date()
    return Jwts.builder()
      .setClaims(claims)
      .setIssuedAt(now)
      .setExpiration(Date(now.time + validTime))
      .signWith(secretKey, SignatureAlgorithm.HS256)
      .compact()
  }

  fun createAccessToken(userPk: String): String {
    return createToken(userPk, accessTokenValidTime)
  }

  fun createRefreshToken(userPk: String): String {
    return createToken(userPk, refreshTokenValidTime)
  }

  fun getAuthentication(token: String): Authentication {
    val user = userQueryOutputPort.getById(UUID.fromString(getIdFromToken(token)))
    val userDetails = UserSecurity(user);
    return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
  }


  fun resolveToken(request: HttpServletRequest): String? {
    val token = request.getHeader("Authorization")
    return if (token != null && token.indexOf("Bearer ") > -1) token.replace("Bearer ", "") else ""
  }

  fun validateToken(token: String?): Boolean {
    return try {
      val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
      !claims.body.expiration.before(Date())
    } catch (e: JwtException) {
      false
    } catch (e: IllegalArgumentException) {
      false
    }
  }
}
