package com.herecity.user.application.dto

import com.herecity.user.domain.UserRole
import com.herecity.user.domain.entity.User
import java.util.*

data class UserDto(
  val id: UUID?,
  val email: String,
  val displayName: String,
  val role: UserRole
) {
  constructor(user: User) : this(id = user.id, email = user.email, displayName = user.displayName, role = user.role)
}
