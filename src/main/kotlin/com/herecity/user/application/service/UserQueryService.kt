package com.herecity.user.application.service

import com.herecity.user.application.dto.UserDto
import com.herecity.user.application.port.inbound.CheckEmailDuplicateQuery
import com.herecity.user.application.port.inbound.FetchUserQuery
import com.herecity.user.application.port.inbound.FetchUsersQuery
import com.herecity.user.application.port.outbound.UserQueryOutputPort
import org.springframework.stereotype.Service

@Service
class UserQueryService(private val userQueryOutputPort: UserQueryOutputPort) : FetchUserQuery, FetchUsersQuery, CheckEmailDuplicateQuery {
    override fun fetchUser(query: FetchUserQuery.In): FetchUserQuery.Out = userQueryOutputPort.getById(query.id).let {
        FetchUserQuery.Out(
            id = it.id,
            email = it.email,
            displayName = it.displayName,
            role = it.role,
            thumbnail = it.thumbnail,
        )
    }

    override fun fetchUsers(query: FetchUsersQuery.In): FetchUsersQuery.Out {
        val userMap = userQueryOutputPort.findAllById(query.ids)
            .map { UserDto(it) }
            .associateBy { it.id }

        return FetchUsersQuery.Out(
            users = userMap
        )
    }

    override fun checkEmailDuplicate(query: CheckEmailDuplicateQuery.In) {
        userQueryOutputPort.findByEmail(query.email).run {
            check(this == null) { "이미 존재하는 이메일입니다." }
        }
    }
}
