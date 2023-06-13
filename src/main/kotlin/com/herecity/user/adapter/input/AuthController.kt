package com.herecity.user.adapter.input

import com.herecity.user.adapter.input.request.SignInRequest
import com.herecity.user.adapter.input.request.SignUpRequest
import com.herecity.user.adapter.input.response.SignInResponse
import com.herecity.user.adapter.input.response.SignUpResponse
import com.herecity.user.application.port.input.SignInQuery
import com.herecity.user.application.port.input.SignUpCommand
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val signInQuery: SignInQuery,
    private val signUpCommand: SignUpCommand,
) {
    @Operation(summary = "로그인")
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("sign-in")
    fun signIn(@RequestBody signInRequest: SignInRequest): SignInResponse =
        signInQuery.signIn(signInRequest.toDomain()).let {
            SignInResponse(
                accessToken = it.accessToken,
                refreshToken = it.refreshToken,
                id = it.id,
                email = it.email,
                displayName = it.displayName,
                role = it.role,
                thumbnail = it.thumbnail,
            )
        }

    @Operation(summary = "회원가입")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("sign-up")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): SignUpResponse = signUpCommand.signUp(signUpRequest.toDomain()).let {
        SignUpResponse(
            accessToken = it.accessToken,
            refreshToken = it.refreshToken,
            id = it.id,
            email = it.email,
            displayName = it.displayName,
            role = it.role,
            thumbnail = it.thumbnail,
        )
    }
}
