package com.herecity.user.application.usecase

import com.herecity.user.application.dto.UserDto
import com.herecity.user.domain.UserRole


interface FakeSignUseCase {
  fun fakeSignIn(role: UserRole = UserRole.USER): UserDto
}
