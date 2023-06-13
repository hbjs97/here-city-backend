package com.herecity.user.adapter.input.response

import com.herecity.user.domain.vo.UserRole
import java.util.UUID

data class SignUpResponse(
    val accessToken: String,
    val refreshToken: String,
    val id: UUID,
    val email: String,
    val displayName: String,
    val role: UserRole,
    val thumbnail: String?,
)
