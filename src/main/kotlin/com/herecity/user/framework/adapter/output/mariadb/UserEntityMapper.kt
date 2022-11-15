package com.herecity.user.framework.adapter.output.mariadb

import com.herecity.user.domain.entity.User

object UserEntityMapper {
  fun userEntityToDomain(userEntity: UserEntity): User {
    return User(userEntity)
  }
}
