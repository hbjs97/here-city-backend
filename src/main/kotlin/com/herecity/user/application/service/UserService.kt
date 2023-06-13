package com.herecity.user.application.service

import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.input.CheckEmailDuplicateQuery
import com.herecity.user.application.port.input.FetchUserUseCase
import com.herecity.user.application.port.output.UserQueryOutputPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(private val userQueryOutputPort: UserQueryOutputPort) : FetchUserUseCase, CheckEmailDuplicateQuery {
    override fun fetchUser(id: UUID): UserDto = UserDto(userQueryOutputPort.getById(id))
    override fun fetchUsers(ids: List<UUID>): List<UserDto> = userQueryOutputPort.findAllById(ids).map { UserDto(it) }
    override fun checkEmailDuplicate(query: CheckEmailDuplicateQuery.In) {
        userQueryOutputPort.findByEmail(query.email).run {
            check(this == null) { "이미 존재하는 이메일입니다." }
        }
    }
}
