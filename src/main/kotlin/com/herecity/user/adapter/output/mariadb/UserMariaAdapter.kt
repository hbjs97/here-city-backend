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
  override fun getById(id: UUID): User = userRepository.findById(id).orElseThrow()
  override fun findById(id: UUID): User? = userRepository.findById(id).get()
  override fun findByEmail(email: String): User? = userRepository.findByEmail(email)
  override fun findAllById(ids: List<UUID>): List<User> = userRepository.findAllById(ids)
  override fun save(entity: User): User = userRepository.save(entity)
}
