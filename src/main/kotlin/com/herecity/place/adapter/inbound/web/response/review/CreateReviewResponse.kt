package com.herecity.place.adapter.inbound.web.response.review

import java.time.ZonedDateTime
import java.util.UUID

data class CreateReviewResponse(
    val id: Long,
    val placeId: Long,
    val tourId: Long?,
    val rating: Double,
    val content: String,
    val images: List<String>,
    val createdAt: ZonedDateTime,
    val createdBy: UUID,
    val userDisplayName: String,
    val userThumbnail: String?,
)
