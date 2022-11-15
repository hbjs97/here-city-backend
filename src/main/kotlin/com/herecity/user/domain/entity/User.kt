package com.herecity.user.domain.entity

import com.herecity.common.domain.entity.BaseDomain
import com.herecity.user.domain.UserRole
import com.herecity.user.framework.adapter.output.mariadb.UserEntity
import java.util.*

class User(entity: UserEntity) : BaseDomain(entity) {
  var id: UUID
  var email: String
  var displayName: String
  var password: String
  var role: UserRole

  init {
    this.id = entity.id!!
    this.email = entity.email
    this.password = entity.password
    this.displayName = entity.displayName
    this.role = entity.role
  }
}
