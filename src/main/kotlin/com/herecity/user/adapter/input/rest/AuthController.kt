package com.herecity.user.adapter.input.rest

import com.herecity.user.adapter.input.rest.dto.FakeSignInDto
import com.herecity.user.application.dto.AuthenticatedPayloadDto
import com.herecity.user.application.port.input.FakeSignUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val fakeSignUseCase: FakeSignUseCase) {
    @Operation(summary = "fake 로그인")
    @ApiResponse(responseCode = "201", description = "가입 및 로그인 성공")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("fake-login")
    fun fakeSignIn(@RequestBody fakeSignInDto: FakeSignInDto): AuthenticatedPayloadDto {
        return fakeSignUseCase.fakeSignIn(fakeSignInDto.role)
    }
}
