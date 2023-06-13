package com.herecity.user.application.dto

import com.herecity.user.domain.entity.User
import com.herecity.user.domain.vo.UserRole
import java.util.UUID

data class UserDto(
    val id: UUID,
    val email: String?,
    val displayName: String,
    val role: UserRole,
    val thumbnail: String?,
) {
    constructor(user: User) : this(
        id = user.id,
        email = user.email,
        displayName = user.displayName,
        role = user.role,
        thumbnail = user.thumbnail
    )
}
