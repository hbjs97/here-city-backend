package com.herecity.user.application.port.inbound

import com.herecity.user.domain.vo.UserRole
import java.util.UUID

interface FetchUserQuery {
    fun fetchUser(query: In): Out
    data class In(
        val id: UUID,
    )

    data class Out(
        val id: UUID,
        val email: String?,
        val displayName: String,
        val role: UserRole,
        val thumbnail: String?,
    )
}
