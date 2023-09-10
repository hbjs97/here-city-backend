package com.herecity.user.application.port.inbound

import com.herecity.user.application.dto.UserDto
import java.util.UUID

interface FetchUsersQuery {
    fun fetchUsers(query: In): Out
    data class In(
        val ids: List<UUID>,
    )

    data class Out(
        val users: Map<UUID, UserDto>,
    )
}
