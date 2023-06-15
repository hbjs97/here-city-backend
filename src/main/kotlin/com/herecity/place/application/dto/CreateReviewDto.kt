package com.herecity.place.application.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class CreateReviewDto(
    val placeId: Long,
    val tourId: Long?,
    @field:Min(1) @field:Max(5)
    val rating: Double,
    val content: String,
    val images: List<String> = emptyList(),
)
