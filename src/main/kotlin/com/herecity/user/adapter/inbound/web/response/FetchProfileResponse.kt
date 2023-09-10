package com.herecity.user.adapter.inbound.web.response

import com.herecity.user.domain.vo.UserRole
import java.util.UUID

data class FetchProfileResponse(
    val id: UUID,
    val email: String?,
    val displayName: String,
    val role: UserRole,
    val thumbnail: String?,
)
