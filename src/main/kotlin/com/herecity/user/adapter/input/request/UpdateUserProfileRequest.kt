package com.herecity.user.adapter.input.request

import com.herecity.user.application.port.input.UpdateUserProfileCommand
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class UpdateUserProfileRequest(
    val displayName: String?,
    val thumbnail: MultipartFile?,
) {
    fun toDomain(userId: UUID): UpdateUserProfileCommand.In {
        require(displayName != null || thumbnail != null) { "displayName or thumbnail is required" }
        return UpdateUserProfileCommand.In(
            userId = userId,
            displayName = displayName,
            thumbnail = thumbnail,
        )
    }
}
