package com.herecity.user.application.usecase

import com.herecity.user.application.dto.UserDto

interface SignUseCase {
  fun signIn(email: String, password: String): UserDto
  fun signOut(): Any
}
