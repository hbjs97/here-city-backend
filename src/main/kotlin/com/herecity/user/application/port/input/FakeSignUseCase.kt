package com.herecity.user.application.port.input

import com.herecity.user.application.dto.JwtToken
import com.herecity.user.domain.UserRole

interface FakeSignUseCase {
  fun fakeSignIn(role: UserRole): JwtToken
}
