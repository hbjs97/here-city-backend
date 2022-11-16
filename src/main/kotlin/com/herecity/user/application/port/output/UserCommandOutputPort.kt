package com.herecity.user.application.port.output

import com.herecity.user.domain.entity.User

interface UserCommandOutputPort {
  fun save(user: User): User
}
