package com.herecity.user.application.port.output

import com.herecity.user.domain.entity.User
import java.util.*

interface UserQueryOutputPort {
  fun getById(id: UUID): User
  fun findById(id: UUID): User?
  fun findByEmail(email: String): User?
}
