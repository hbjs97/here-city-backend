package com.herecity.user.application.port.input

import com.herecity.user.application.dto.AuthenticatedPayloadDto
import com.herecity.user.domain.vo.UserRole

interface FakeSignUseCase {
  fun fakeSignIn(role: UserRole): AuthenticatedPayloadDto
}
