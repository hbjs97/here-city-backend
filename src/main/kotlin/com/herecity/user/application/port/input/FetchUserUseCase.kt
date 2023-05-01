package com.herecity.user.application.port.input

import com.herecity.user.application.dto.UserDto
import java.util.*

interface FetchUserUseCase {
  fun fetchUser(id: UUID): UserDto
  fun fetchUsers(ids: List<UUID>): List<UserDto>
}