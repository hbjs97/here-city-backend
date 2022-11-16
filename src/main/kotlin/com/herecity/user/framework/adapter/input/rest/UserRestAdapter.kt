package com.herecity.user.framework.adapter.input.rest

import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.input.FakeSignInputPort
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/users")
class UserRestAdapter(private val fakeSignUseCase: FakeSignInputPort) {

  @Operation(summary = "fake 로그인")
  @ApiResponses(
    ApiResponse(responseCode = "201", description = "가입 및 로그인 성공"),
  )
  @ResponseStatus(value = HttpStatus.CREATED)
  @PostMapping("fake-login")
  fun fakeSignIn(): UserDto {
    return fakeSignUseCase.fakeSignIn()
  }
}
