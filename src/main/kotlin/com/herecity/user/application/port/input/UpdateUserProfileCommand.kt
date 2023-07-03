package com.herecity.user.application.port.input

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface UpdateUserProfileCommand {
    fun updateProfile(command: In): Out
    data class In(
        val userId: UUID,
        val displayName: String?,
        val thumbnail: MultipartFile?,
    )

    data class Out(
        val id: UUID,
        val displayName: String,
        val thumbnail: String?,
    )
}
