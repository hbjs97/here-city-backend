package com.herecity.user.framework.adapter.output.mariadb

import com.herecity.user.domain.entity.User

object UserEntityMapper {
  fun userEntityToDomain(userEntity: UserEntity): User {
    return User(userEntity)
  }

  fun userDomainToEntity(user: User): UserEntity {
    return UserEntity(
      id = user.id,
      email = user.email,
      displayName = user.displayName,
      password = user.password,
      role = user.role
    )
  }
}
