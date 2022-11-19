package com.herecity.user.application.security

import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.output.UserQueryOutputPort
import com.herecity.user.domain.UserDetail
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtService(private val userQueryOutputPort: UserQueryOutputPort) {
  companion object {
    private val secretKey: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    private const val accessTokenValidTime = 30 * 60 * 1000L

    private const val refreshTokenValidTime = 30 * 24 * 60 * 60 * 1000L

    fun getIdFromToken(token: String): String {
      return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    }
  }

  fun createAccessToken(userDto: UserDto): String {

    val claims = HashMap<String, String>()
    claims["id"] = userDto.id.toString()
    claims["email"] = userDto.email
    claims["displayName"] = userDto.displayName
    claims["role"] = userDto.role.name
    
    val now = Date()
    return Jwts.builder()
      .setClaims(claims)
      .setSubject(userDto.id.toString())
      .setIssuedAt(now)
      .setExpiration(Date(now.time + accessTokenValidTime))
      .signWith(secretKey, SignatureAlgorithm.HS256)
      .compact()
  }

  fun createRefreshToken(accessToken: String): String {
    val now = Date()
    return Jwts.builder()
      .setSubject(accessToken)
      .setIssuedAt(now)
      .setExpiration(Date(now.time + refreshTokenValidTime))
      .signWith(secretKey, SignatureAlgorithm.HS256)
      .compact()
  }

  fun getAuthentication(token: String, passwordEncoder: PasswordEncoder): Authentication {
    val user = userQueryOutputPort.getById(UUID.fromString(getIdFromToken(token)))
    val userDetails = UserDetail(user, passwordEncoder)
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
