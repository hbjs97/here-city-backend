package com.herecity.user.application.service

import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.input.FetchUserUseCase
import com.herecity.user.application.port.output.UserQueryOutputPort
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userQueryOutputPort: UserQueryOutputPort) : FetchUserUseCase {
  override fun fetchUser(id: UUID): UserDto = UserDto(userQueryOutputPort.getById(id))
  override fun fetchUsers(ids: List<UUID>): List<UserDto> = userQueryOutputPort.findAllById(ids).map { UserDto(it) }
}
