package com.herecity.user.application.service

import com.herecity.user.application.dto.JwtToken
import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.input.FakeSignUseCase
import com.herecity.user.application.port.output.UserCommandOutputPort
import com.herecity.user.application.port.output.UserQueryOutputPort
import com.herecity.user.application.security.JwtService
import com.herecity.user.domain.entity.User
import com.herecity.user.domain.exception.SignInFailException
import com.herecity.user.domain.vo.UserRole
import io.github.serpro69.kfaker.Faker
import mu.KotlinLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service
import java.util.*

private val logger = KotlinLogging.logger {}

@Service
class AuthService(
  private val userQuery: UserQueryOutputPort,
  private val userCommand: UserCommandOutputPort,
  private val authenticationManager: AuthenticationManager,
  private val jwtService: JwtService,
  private val userSecurityService: UserSecurityService
) : FakeSignUseCase {
  fun signIn(email: String, password: String): UserDto {
    TODO("Not yet implemented")
  }


  fun signOut(): Any {
    TODO("Not yet implemented")
  }

  override fun fakeSignIn(role: UserRole): JwtToken {
    val user = userCommand.save(
      User(
        id = UUID.randomUUID(),
        email = Faker().internet.unique.email(),
        displayName = Faker().name.name().chunked(30).first(),
        role = role,
        twitterId = Faker().name.name().chunked(30).first(),
      )
    )
    try {
      val userDetail = userSecurityService.loadUserByUsername(user.email)
      authenticationManager.authenticate(UsernamePasswordAuthenticationToken(userDetail, "", userDetail.authorities))

    } catch (e: AuthenticationException) {
      logger.error { e.message }
      throw SignInFailException()
    }

    val accessToken = jwtService.createAccessToken(UserDto(user))
    val refreshToken = jwtService.createRefreshToken(accessToken)
    return JwtToken(accessToken, refreshToken)
  }
}
