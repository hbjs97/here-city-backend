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

  override fun findByEmail(email: String): User? = userRepository.findByEmail(email)

  override fun save(user: User): User = userRepository.save(user)
}
