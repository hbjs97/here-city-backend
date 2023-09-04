package com.herecity.place.application.port.input.review

import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.UUID

interface CreatePlaceReviewCommand {
    fun review(command: In): Out
    data class In(
        val userId: UUID,
        val placeId: Long,
        val tourId: Long?,
        val rating: Double,
        val content: String,
        val images: List<MultipartFile> = emptyList(),
    )

    data class Out(
        val id: Long,
        val placeId: Long,
        val tourId: Long?,
        val rating: Double,
        val content: String,
        val images: List<String>,
        val createdAt: LocalDateTime,
        val createdBy: UUID,
        val userDisplayName: String,
        val userThumbnail: String?,
    )
}
