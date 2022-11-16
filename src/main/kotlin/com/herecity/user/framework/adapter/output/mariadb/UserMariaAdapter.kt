package com.herecity.user.framework.adapter.output.mariadb

import com.herecity.user.application.port.output.UserCommandOutputPort
import com.herecity.user.application.port.output.UserQueryOutputPort
import com.herecity.user.domain.entity.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserMariaAdapter(
  private val userRepository: UserRepository
) : UserQueryOutputPort, UserCommandOutputPort {
  override fun getById(id: UUID): User {
    val userEntity = userRepository.findById(id).orElseThrow()
    return UserEntityMapper.userEntityToDomain(userEntity)
  }

  override fun findById(id: UUID): User? {
    val userEntity = userRepository.findById(id).get()
    return UserEntityMapper.userEntityToDomain(userEntity)
  }

  override fun findByEmail(email: String): User? {
    val userEntity = userRepository.findByEmail(email) ?: return null
    return UserEntityMapper.userEntityToDomain(userEntity)
  }

  override fun save(user: User): User {
    val userEntity = userRepository.save(UserEntityMapper.userDomainToEntity(user))
    return UserEntityMapper.userEntityToDomain(userEntity)
  }
}
