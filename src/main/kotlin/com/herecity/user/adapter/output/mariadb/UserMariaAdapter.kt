package com.herecity.user.adapter.output.mariadb

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
    return userRepository.findById(id).orElseThrow()
  }

  override fun findById(id: UUID): User? {
    return userRepository.findById(id).get()
  }

  override fun findByEmail(email: String): User? {
    return userRepository.findByEmail(email) ?: null
  }

  override fun save(user: User): User {
    return userRepository.save(user)
  }
}
