package com.herecity.place.application.dto

import java.time.LocalDateTime
import java.util.UUID

data class PlaceReviewDto(
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
