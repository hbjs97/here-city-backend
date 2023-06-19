package com.herecity.user.application.port.input

import com.herecity.user.domain.vo.UserRole
import java.util.UUID

interface SignInQuery {
    fun signIn(query: In): Out

    data class In(
        val email: String,
        val password: String,
    )

    data class Out(
        val accessToken: String,
        val refreshToken: String,
        val id: UUID,
        val email: String?,
        val displayName: String,
        val role: UserRole,
        val thumbnail: String?,
    )
}
