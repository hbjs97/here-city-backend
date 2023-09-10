package com.herecity.user.adapter.outbound.mariadb

import com.herecity.user.application.port.outbound.UserCommandOutputPort
import com.herecity.user.application.port.outbound.UserQueryOutputPort
import com.herecity.user.domain.entity.User
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserMariaAdapter(
    private val userRepository: UserRepository,
) : UserQueryOutputPort, UserCommandOutputPort {
    override fun getById(id: UUID): User = userRepository.findById(id).orElseThrow()
    override fun findById(id: UUID): User? = userRepository.findById(id).get()
    override fun findByEmail(email: String): User? = userRepository.findByEmail(email)
    override fun findByProviderId(providerId: String): User? = userRepository.findByProviderId(providerId)

    override fun findAllById(ids: List<UUID>): List<User> = userRepository.findAllById(ids)
    override fun save(entity: User): User = userRepository.save(entity)
}
