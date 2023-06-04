package com.herecity.user.application.service

import com.herecity.user.application.dto.AuthenticatedPayloadDto
import com.herecity.user.application.dto.JwtToken
import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.input.FakeSignUseCase
import com.herecity.user.application.port.output.UserCommandOutputPort
import com.herecity.user.application.security.JwtService
import com.herecity.user.domain.entity.User
import com.herecity.user.domain.vo.UserRole
import io.github.serpro69.kfaker.Faker
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthService(
    private val userCommand: UserCommandOutputPort,
    private val jwtService: JwtService,
) : FakeSignUseCase {
    fun signIn(email: String, password: String): UserDto {
        TODO("Not yet implemented")
    }

    fun signOut(): Any {
        TODO("Not yet implemented")
    }

    override fun fakeSignIn(role: UserRole): AuthenticatedPayloadDto {
        val user = userCommand.save(
            User(
                id = UUID.randomUUID(),
                email = Faker().internet.unique.email(),
                displayName = Faker().name.name().chunked(30).first(),
                role = role,
                twitterId = Faker().name.name().chunked(30).first(),
            )
        )

        val accessToken = jwtService.createAccessToken(UserDto(user))
        val refreshToken = jwtService.createRefreshToken(accessToken)
        return AuthenticatedPayloadDto(JwtToken(accessToken, refreshToken), UserDto(user))
    }
}
