package com.herecity.user.adapter.input.response

import java.util.UUID

data class UpdateUserProfileResponse(
    val id: UUID,
    val displayName: String,
    val thumbnail: String?,
)
