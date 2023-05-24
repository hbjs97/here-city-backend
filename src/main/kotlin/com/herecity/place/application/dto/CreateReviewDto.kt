package com.herecity.place.application.dto

import javax.validation.constraints.Size

data class CreateReviewDto(
    val placeId: Long,
    val tourId: Long?,
    @field:Size(min = 1, max = 5)
    val rating: Int,
    val content: String,
    val images: List<String> = emptyList(),
)
