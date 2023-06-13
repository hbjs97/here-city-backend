package com.herecity.user.application.service

import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.input.CheckEmailDuplicateQuery
import com.herecity.user.application.port.input.SignInQuery
import com.herecity.user.application.port.input.SignUpCommand
import com.herecity.user.application.port.output.UserCommandOutputPort
import com.herecity.user.application.port.output.UserQueryOutputPort
import com.herecity.user.application.security.JwtService
import com.herecity.user.domain.entity.User
import com.herecity.user.domain.vo.UserRole
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userQueryOutputPort: UserQueryOutputPort,
    private val userCommandOutputPort: UserCommandOutputPort,
    private val passwordEncoder: PasswordEncoder,
    private val checkEmailDuplicateQuery: CheckEmailDuplicateQuery,
) : SignInQuery, SignUpCommand {
    override fun signIn(query: SignInQuery.In): SignInQuery.Out {
        val user = userQueryOutputPort.findByEmail(query.email)
        checkNotNull(user) { "존재하지 않는 계정입니다." }
        check(
            passwordEncoder.matches(
                query.password,
                user.password
            )
        ) { "비밀번호가 일치하지 않습니다." }
        val accessToken = jwtService.createAccessToken(UserDto(user))
        val refreshToken = ""
        return SignInQuery.Out(
            accessToken = accessToken,
            refreshToken = refreshToken,
            id = user.id,
            email = user.email,
            displayName = user.displayName,
            role = user.role,
            thumbnail = user.thumbnail,
        )
    }

    override fun signUp(command: SignUpCommand.In): SignUpCommand.Out {
        checkEmailDuplicateQuery.checkEmailDuplicate(
            CheckEmailDuplicateQuery.In(
                email = command.email
            )
        )

        userCommandOutputPort.save(
            User(
                email = command.email,
                password = passwordEncoder.encode(command.password),
                displayName = command.displayName,
                provider = null,
                role = UserRole.USER,
            )
        ).let {
            return SignUpCommand.Out(
                accessToken = jwtService.createAccessToken(UserDto(it)),
                refreshToken = "",
                id = it.id,
                email = it.email,
                displayName = it.displayName,
                role = it.role,
                thumbnail = it.thumbnail,
            )
        }
    }
}
