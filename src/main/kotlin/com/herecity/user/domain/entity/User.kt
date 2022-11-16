package com.herecity.user.domain.entity

import com.herecity.common.domain.entity.BaseDomain
import com.herecity.user.domain.UserRole
import com.herecity.user.framework.adapter.output.mariadb.UserEntity
import java.util.*

class User(entity: UserEntity) : BaseDomain(entity) {
  var id: UUID? = entity.id
  var email: String = entity.email
  var displayName: String = entity.displayName
  var password: String = entity.password
  var role: UserRole = entity.role

}
