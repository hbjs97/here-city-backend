package com.herecity.user.application.port.output

import com.herecity.common.adapter.mariadb.BaseQueryRepository
import com.herecity.user.domain.entity.User
import java.util.*

interface UserQueryOutputPort : BaseQueryRepository<User, UUID> {
  fun findByEmail(email: String): User?
}
