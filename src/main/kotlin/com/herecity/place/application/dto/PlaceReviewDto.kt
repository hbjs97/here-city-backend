package com.herecity.place.application.dto

import java.time.LocalDateTime
import java.util.*

data class PlaceReviewDto(
    val id: Long,
    val placeId: Long,
    val tourId: Long?,
    val createdBy: UUID,
    val rating: Int,
    val content: String,
    val images: List<String>,
    val createdAt: LocalDateTime,
)
